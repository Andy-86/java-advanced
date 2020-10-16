package org.lesson.one;

public class Hello {

    private int a;
    public static int b;
    public static final int c = 3;
    private static final int d = 4;

    public static void main(String[] args) {

    }

    public String say(String something) {
        System.out.println("say something");
        return something;
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int sub(int a, int b) {
        return a - b;
    }

    public static int mul(int a, int b) {
        return a * b;
    }

    public static int div(int a, int b) {
        return a / b;
    }

    public static int re(int a, int b) {
        return a % b;
    }

    public void testForLoop() {
        for (int i = 0; i < 10; i++) {
            System.out.println("for loop i = " + i);
        }
    }

    public void testForeachLoop() {
        int[] array = new int[]{1, 2, 3};
        for (int i : array) {
            System.out.println("foreach loop i = " + i);
        }
    }

    private void testTryCatchException() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    private void testThrowRuntimeException(String food) {
        throw new IllegalStateException("a illegalState exception");
    }

}
