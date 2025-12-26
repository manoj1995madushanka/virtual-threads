package org.example;

public class InboundOutboundTaskDemo {
    private static final int MAX_THREADS = 500;

    static void main(String[] args) {
        platformThreadDaemonDemo();
    }

    private static void platformThreadDemo() {
        for (int i = 0; i < MAX_THREADS; i++) {
            int j = i;
            Thread thread = new Thread(() -> {
                PlatformThreadLimit.ioIntensive(j);
            });
            thread.start();
        }
    }

    private static void platformThreadDaemonDemo() {
        try {
            var latch = new java.util.concurrent.CountDownLatch(MAX_THREADS);
            var builder = Thread.ofPlatform().daemon().name("platformThreadDaemonDemo", 1);
            for (int i = 0; i < MAX_THREADS; i++) {
                int j = i;
                Thread thread = builder.unstarted(() -> {
                    PlatformThreadLimit.ioIntensive(j);
                    latch.countDown();
                });
                thread.start();
            }
            latch.await(); // we can also use join on each thread
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
