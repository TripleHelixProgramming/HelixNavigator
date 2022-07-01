package org.team2363.lib.math;

import java.util.ArrayList;
import java.util.List;

// This code was copied from https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
// I have modified some stuff a little to add scientific notation and pi and e
// Thank you so much!
public class ExpressionParser {

    public static class ExpressionSyntaxException extends RuntimeException {
        private ExpressionSyntaxException(String message) {
            super(message);
        }
    }

    @FunctionalInterface
    private static interface Function {
        public default int argCount() {return 1;};
        public double eval(double... args);
        public default double checkedEval(double... args) throws ExpressionSyntaxException {
            if (args.length != argCount()) {
                throw new ExpressionSyntaxException("Function accepts exactly " + argCount() + " argument" + (argCount() == 1 ? "." : "s."));
            }
            return eval(args);
        }
    }

    private static final Function sqrt  = args -> Math.sqrt(args[0]);
    private static final Function cbrt  = args -> Math.cbrt(args[0]);
    private static final Function sin   = args -> Math.sin(args[0]);
    private static final Function cos   = args -> Math.cos(args[0]);
    private static final Function tan   = args -> Math.tan(args[0]);
    private static final Function csc   = args -> 1 / Math.sin(args[0]);
    private static final Function sec   = args -> 1 / Math.cos(args[0]);
    private static final Function cot   = args -> Math.cos(args[0]) / Math.sin(0);
    private static final Function asin  = args -> Math.asin(args[0]);
    private static final Function acos  = args -> Math.acos(args[0]);
    private static final Function atan  = args -> Math.atan(args[0]);
    private static final Function acsc  = args -> Math.asin(1 / args[0]);
    private static final Function asec  = args -> Math.acos(1 / args[0]);
    private static final Function acot  = args -> Math.PI / 2 - Math.atan(args[0]);
    private static final Function sinh  = args -> Math.sinh(args[0]);
    private static final Function cosh  = args -> Math.cosh(args[0]);
    private static final Function tanh  = args -> Math.tanh(args[0]);
    private static final Function csch  = args -> 1 / Math.sinh(args[0]);
    private static final Function sech  = args -> 1 / Math.cosh(args[0]);
    private static final Function coth  = args -> Math.cosh(args[0]) / Math.sinh(args[0]);
    private static final Function asinh = args -> Math.log(args[0] + Math.sqrt(args[0] * args[0] + 1.0));
    private static final Function acosh = args -> Math.log(args[0] + Math.sqrt(args[0] * args[0] - 1.0));
    private static final Function atanh = args -> 0.5 * Math.log((1 + args[0]) / (1 - args[0]));
    private static final Function acsch = args -> Math.log(1 / args[0] + Math.sqrt(1 / (args[0] * args[0]) + 1));
    private static final Function asech = args -> Math.log(1 / args[0] + Math.sqrt(1 / (args[0] * args[0]) - 1));
    private static final Function acoth = args -> 0.5 * Math.log((args[0] + 1) / (args[0] - 1));
    private static final Function hypot = new Function() {
        @Override
        public int argCount() {
            return 2;
        }
        @Override
        public double eval(double... args) {
            return Math.hypot(args[0], args[1]);
        }
    };
    private static final Function atan2 = new Function() {
        @Override
        public int argCount() {
            return 2;
        }
        @Override
        public double eval(double... args) {
            return Math.atan2(args[0], args[1]);
        }
    };
    private static final Function abs = args -> Math.abs(args[0]);
    private static final Function signum = args -> Math.signum(args[0]);
    private static final Function round = args -> Math.round(args[0]);
    private static final Function floor = args -> Math.floor(args[0]);
    private static final Function ceil = args -> Math.ceil(args[0]);
    private static final Function min = new Function() {
        @Override
        public int argCount() {
            return 2;
        }
        @Override
        public double eval(double... args) {
            return Math.min(args[0], args[1]);
        }
    };
    private static final Function max = new Function() {
        @Override
        public int argCount() {
            return 2;
        }
        @Override
        public double eval(double... args) {
            return Math.max(args[0], args[1]);
        }
    };
    private static final Function deg = args -> Math.toDegrees(args[0]);
    private static final Function rad = args -> Math.toRadians(args[0]);
    private static final Function pi = new Function() {
        @Override
        public int argCount() {
            return 0;
        }
        @Override
        public double eval(double... args) {
            return Math.PI;
        }
    };
    private static final Function e = new Function() {
        @Override
        public int argCount() {
            return 0;
        }
        @Override
        public double eval(double... args) {
            return Math.E;
        }
    };

    static Function functionFor(String functionName) throws ExpressionSyntaxException {
        switch (functionName) {
            case "sqrt": return sqrt;
            case "cbrt": return cbrt;
            case "sin": return sin;
            case "cos": return cos;
            case "tan": return tan;
            case "csc": return csc;
            case "sec": return sec;
            case "cot": return cot;
            case "asin": return asin;
            case "acos": return acos;
            case "atan": return atan;
            case "acsc": return acsc;
            case "asec": return asec;
            case "acot": return acot;
            case "sinh": return sinh;
            case "cosh": return cosh;
            case "tanh": return tanh;
            case "csch": return csch;
            case "sech": return sech;
            case "coth": return coth;
            case "asinh": return asinh;
            case "acosh": return acosh;
            case "atanh": return atanh;
            case "acsch": return acsch;
            case "asech": return asech;
            case "acoth": return acoth;
            case "hypot": return hypot;
            case "atan2": return atan2;
            case "abs": return abs;
            case "signum": return signum;
            case "round": return round;
            case "floor": return floor;
            case "ceil": return ceil;
            case "min": return min;
            case "max": return max;
            case "deg": return deg;
            case "rad": return rad;
            case "pi": return pi;
            case "e": return e;
            default:
                throw new ExpressionSyntaxException("No function named \"" + functionName + "\" found.");
        }
    }

    public static double eval(final String str) throws ExpressionSyntaxException {
        return new ExpressionParser(str).parse();
    }

    private String str;
    private int pos = -1, ch;

    private ExpressionParser(String str) {
        this.str = str;
    }

    private void nextChar() {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (Character.isWhitespace(ch)) nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    private double parse() throws ExpressionSyntaxException {
        nextChar();
        double x = parseExpression();
        if (pos < str.length()) throw new ExpressionSyntaxException("Unexpected: " + (char)ch);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)` | number
    //        | functionName `(` expression `)` | functionName factor
    //        | factor `^` factor
    
    private double parseExpression() throws ExpressionSyntaxException {
        double x = parseTerm();
        for (;;) {
            if      (eat('+')) x += parseTerm(); // addition
            else if (eat('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }
    
    private double parseTerm() throws ExpressionSyntaxException {
        double x = parseFactor();
        for (;;) {
            if      (eat('*')) x *= parseFactor(); // multiplication
            else if (eat('/')) x /= parseFactor(); // division
            else if (eat('%')) x %= parseFactor(); // modulus
            else return x;
        }
    }
    
    private double parseFactor() throws ExpressionSyntaxException {
        if (eat('+')) return +parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus
        
        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
            x = parseExpression();
            if (!eat(')')) throw new ExpressionSyntaxException("Missing ')'");
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.' || ch == 'e' || ch == 'E') {
                if (ch == 'e' || ch == 'E') {
                    nextChar();
                    if (ch == '-') {
                        nextChar();
                    }
                } else {
                    nextChar();
                }
            }
            x = Double.parseDouble(str.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') { // constants or functions
            while ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) nextChar();
            String name = str.substring(startPos, this.pos);
            List<Double> args = new ArrayList<>();
            if (eat('(')) { // if function
                do {
                    args.add(parseExpression());
                } while (eat(','));
                if (!eat(')')) throw new ExpressionSyntaxException("Missing ')' after argument to \"" + name + "\"");
            }
            Function func = functionFor(name);
            x = func.checkedEval(args.stream().mapToDouble(Double::doubleValue).toArray());
        } else {
            throw new ExpressionSyntaxException("Unexpected: " + (char)ch);
        }
        
        if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
        
        return x;
    }
}
