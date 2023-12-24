package com.edev.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello, welcome to you!";
    }
}
