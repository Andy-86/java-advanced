public class DaemonThread {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("current thread name :" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(task);
        t.setName("daemon-thread");
        t.setDaemon(true);
        t.start();
    }
}
