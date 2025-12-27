package org.example;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {
    private static final int MAX_THREADS = 500;
    private static final int MAX_V_THREADS = 5000;

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

    private static void platformThreadUsingBuilderDemo() {
        var builder = Thread.ofPlatform().name("platformThreadDemo", 1);
        for (int i = 0; i < MAX_THREADS; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
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

    private static void virtualThreadDemo(){
        var latch = new CountDownLatch(MAX_V_THREADS);
        var builder = Thread.ofVirtual();
        for(int i = 0;i<MAX_V_THREADS;i++){
            int j = i;
            Thread thread = builder.unstarted(()->{
                PlatformThreadLimit.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
