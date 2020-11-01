public class Hello {

    private int a;
    public static int b;
    public static final int c = 3;
    private static final int d = 4;
    private volatile double f;

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.say("hello world");
        Hello.add(1, 2);
        Hello.sub(1000000, 20);
        Hello.mul(1000, 2000);
        Hello.div(500, 3);
        Hello.re(10, 3);
        hello.testForLoop();
        hello.testForeachLoop();
        hello.testTryCatchException();
        hello.testThrowRuntimeException("apple");
        hello.testSynchronized();
        InnerClassTest1 innerClassTest1 = hello.new InnerClassTest1();
        innerClassTest1.name = "moon";
        innerClassTest1.greet();
        InnerClassTest2 innerClassTest2 = new InnerClassTest2();
        innerClassTest2.name = "sky";
        innerClassTest2.greet();
    }

    public void say(String something) {
        System.out.println(something);
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

    public synchronized void testSynchronized() {
        a++;
    }

    private void testThrowRuntimeException(String food) {
        throw new IllegalStateException("a illegalState exception");
    }

    class InnerClassTest1 {
        private String name;

        public void greet() {
            System.out.println("my name is " + name);
        }
    }

    static class InnerClassTest2 {
        private String name;

        public void greet() {
            System.out.println("my name is " + name);
        }
    }

}
