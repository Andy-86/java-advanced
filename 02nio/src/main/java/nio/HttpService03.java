package nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpService03 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8083);
            ThreadFactory threadFactory = new ThreadFactory() {
                private final AtomicInteger se = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("http-nio-thread-" + se.incrementAndGet());
                    return thread;
                }
            };
            ThreadPoolExecutor executors = new ThreadPoolExecutor(8,
                    16,
                    60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(200),
                    threadFactory);
            while (true) {
                Socket socket = serverSocket.accept();
                executors.execute(() -> service(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
