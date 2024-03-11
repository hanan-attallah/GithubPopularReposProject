package org.com.github.popular.repos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health_check")
public class HealthController {
    @RequestMapping(value = {""}, method = RequestMethod.GET, produces = "application/json")
    public String healthCheck() {
        return "{\"status\" : \"Success\"}";
    }
}
