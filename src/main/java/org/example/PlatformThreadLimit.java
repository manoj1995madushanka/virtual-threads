package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PlatformThreadLimit {

    public static final Logger log = LoggerFactory.getLogger(PlatformThreadLimit.class);

    static void ioIntensive(int i) {
        try{
            log.info("Starting I/O task {}", i);
            Thread.sleep(Duration.ofSeconds(10));
            log.info("Finished I/O task {}", i);
        } catch (InterruptedException e) {
            log.error("Thread interrupted: ", e);
        }
    }

}
