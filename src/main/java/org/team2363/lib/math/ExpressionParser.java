package org.team2363.lib.math;
// This code was copied directly from https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
// I have modified some stuff a little to add scientific notation and pi and e
// Thank you so much!
public class ExpressionParser {

    public static class ExpressionSyntaxException extends RuntimeException {
        private ExpressionSyntaxException(String message) {
            super(message);
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
    
    private double parseExpression() {
        double x = parseTerm();
        for (;;) {
            if      (eat('+')) x += parseTerm(); // addition
            else if (eat('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }
    
    private double parseTerm() {
        double x = parseFactor();
        for (;;) {
            if      (eat('*')) x *= parseFactor(); // multiplication
            else if (eat('/')) x /= parseFactor(); // division
            else return x;
        }
    }
    
    private double parseFactor() {
        if (eat('+')) return +parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus
        
        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
            x = parseExpression();
            if (!eat(')')) throw new ExpressionSyntaxException("Missing ')'");
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.' || ch == 'e' || ch == 'E' || ch == '-') nextChar();
            x = Double.parseDouble(str.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') { // constants or functions
            while (ch >= 'a' && ch <= 'z') nextChar();
            String name = str.substring(startPos, this.pos);
            if (eat('(')) {
                x = parseExpression();
                if (!eat(')')) throw new ExpressionSyntaxException("Missing ')' after argument to " + name);
            } else {
                x = 0.0;
            }
            if (name.equals("sqrt")) x = Math.sqrt(x);
            else if (name.equals("sin")) x = Math.sin(x);
            else if (name.equals("cos")) x = Math.cos(x);
            else if (name.equals("tan")) x = Math.tan(x);
            else if (name.equals("asin")) x = Math.asin(x);
            else if (name.equals("acos")) x = Math.acos(x);
            else if (name.equals("atan")) x = Math.atan(x);
            else if (name.equals("pi")) x = Math.PI;
            else if (name.equals("e")) x = Math.E;
            else throw new ExpressionSyntaxException("Unknown function or constant: " + name);
        } else {
            throw new ExpressionSyntaxException("Unexpected: " + (char)ch);
        }
        
        if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
        
        return x;
    }
}
