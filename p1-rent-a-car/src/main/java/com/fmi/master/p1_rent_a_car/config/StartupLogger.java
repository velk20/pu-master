package com.fmi.master.p1_rent_a_car.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {
    Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        logger.info("Swagger documentation: {}", event.getApplicationContext().getEnvironment().getProperty("swagger.url"));
        logger.info("H2 DB url: {}", event.getApplicationContext().getEnvironment().getProperty("h2.url"));
    }
}