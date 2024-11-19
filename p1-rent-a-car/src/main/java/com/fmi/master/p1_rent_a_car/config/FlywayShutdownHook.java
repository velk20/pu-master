package com.fmi.master.p1_rent_a_car.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class FlywayShutdownHook implements DisposableBean {

    private final Flyway flyway;

    public FlywayShutdownHook(Flyway flyway) {
        this.flyway = flyway;
    }

    /**
     * For testing purposes
     * The migrations will execute every time
     * when the application is closed
     */
    @Override
    public void destroy() {
        // Run a Flyway migration script to drop all tables
        flyway.clean(); // This will drop all tables, views, and related schema objects
        System.out.println("Flyway clean executed: All tables dropped");
    }
}
