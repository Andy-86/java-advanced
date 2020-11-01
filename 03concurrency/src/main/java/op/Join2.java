package op;

public class Join2 {
    public static void main(String[] args) {
        Object oo = new Object();

        JoinThread t = new JoinThread(oo);
        t.setName("join-thread");
        t.start();
        synchronized (t) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }

    static class JoinThread extends Thread {
        private Object oo;

        public JoinThread(Object oo) {
            this.oo = oo;
        }

        @Override
        public void run() {
            synchronized (this) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                }
            }
        }
    }
}
