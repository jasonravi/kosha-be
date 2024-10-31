package com.dreamx.kosha.service;

import com.dreamx.kosha.controller.HealthCheckController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HealthCheckScheduler {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private HealthCheckController healthCheckController;

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void performHealthCheck() {

        try {
            ResponseEntity<String> response = healthCheckController.checkHealth();
            log.info("Health check successful. Response: {} " , response.getBody());
        } catch (Exception e) {
            log.error("Health check failed: {}" , e.getMessage());
        }
    }
}
