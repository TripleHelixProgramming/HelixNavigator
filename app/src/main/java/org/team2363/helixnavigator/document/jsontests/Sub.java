package org.team2363.helixnavigator.document.jsontests;

public class Sub extends Super {

    @Override
    public Double specific() {return 0.0;}

    public static void printArray(Object[] arry) {
        System.out.println("[");
        for (Object obj : arry) {
            System.out.println(obj);
        }
        System.out.println("]");
    }
}