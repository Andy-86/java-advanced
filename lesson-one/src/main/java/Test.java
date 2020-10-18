public class Test {
    public static void main(String[] args) {
        System.out.println();
    }

    static class A {
        private final static int a = 1;
    }

    static class B {
        private final static int b = A.a;
    }
}
