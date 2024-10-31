package com.dreamx.kosha.controller;


import com.dreamx.kosha.service.KoshaHealthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping("healthcheck")
public class HealthCheckController {

    @Autowired
    private KoshaHealthService koshaHealthService;

    @GetMapping
    public ResponseEntity<String> checkHealth(){
        boolean healthCheck = koshaHealthService.isDatabaseUp();
        if(healthCheck) {
            return new ResponseEntity<>("Kosha service health is OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Kosha service health is DOWN", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
