public class ThreadInfo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("这是线程B");

            Thread currentThread = Thread.currentThread();
            String currentThreadName = currentThread.getName();

            System.out.println("这是线程的名称：" + currentThreadName);
            System.out.println("返回当前线程" + currentThreadName + "的线程组中活动线程的数量:" + Thread.activeCount());
            System.out.println("返回该线程" + currentThreadName + "的标识符:" + currentThread.getId());
            System.out.println("返回该线程" + currentThreadName + "的优先级:" + currentThread.getPriority());
            System.out.println("返回该线程" + currentThreadName + "的状态:" + currentThread.getState());
            System.out.println("返回该线程" + currentThreadName + "所属的线程组:" + currentThread.getThreadGroup());
            System.out.println("测试该线程" + currentThreadName + "是否处于活跃状态:" + currentThread.isAlive());
            System.out.println("测试该线程" + currentThreadName + "是否为守护线程:" + currentThread.isDaemon());
        });
        t.start();
        System.out.println(Thread.currentThread().getThreadGroup().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getMaxPriority());
        System.out.println(Thread.currentThread().getThreadGroup().getParent());
        System.out.println(Thread.activeCount());
        Thread.currentThread().getThreadGroup().list();
    }
}
