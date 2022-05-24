package edu.gdou.gym_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testPull {
    @RequestMapping("/")
    public String hello() {
        return "test-wu";
    }
}
