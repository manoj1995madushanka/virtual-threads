package org.example;

public class InboundOutboundTaskDemo {
    private static  final int MAX_THREADS = 500;
    static void main(String[] args) {
        platformThreadDemo();
    }

    private static void platformThreadDemo(){
        for(int i=0; i<MAX_THREADS; i++){
            int j = i;
            Thread thread = new Thread(()->{PlatformThreadLimit.ioIntensive(j);});
            thread.start();
        }
    }
}
