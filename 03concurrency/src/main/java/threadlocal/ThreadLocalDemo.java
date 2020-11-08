package threadlocal;

public class ThreadLocalDemo {
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        for (int i = 0; i < 3; i++) {
            new Thread(new ReadNum(demo)).start();
        }
    }

    public Integer getNextNum() {
        threadLocal.set(threadLocal.get() + 1);
        return threadLocal.get();
    }

    static class ReadNum implements Runnable {
        private final ThreadLocalDemo sn;

        public ReadNum(ThreadLocalDemo sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("thread[" + Thread.currentThread().getName() + "] ---> sn [" + sn.getNextNum() + "]");
            }
            sn.threadLocal.remove();
        }
    }
}
