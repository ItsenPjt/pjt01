package com.newcen.newcen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<?> check() {
        log.info("Server is running...!!");
        return ResponseEntity
                .ok()
                .body("Server is running...!!");
    }

}
