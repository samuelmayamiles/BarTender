package com.smm.bartender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * BarTender SpringBoot Application
 * 
 * @author Samuel Maya Miles
 * @version 1.0.0
 * @since 30/07/2017
 *
 */
@SpringBootApplication
public class BarTender implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * BarTender Application Main entry point.
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BarTender.class, args);
    }

    /**
     * Called when Application is ready.
     */
    public void onApplicationEvent(ApplicationReadyEvent arg0) {
        System.out.println("BarTender is up and Runnning...");
    }
}
