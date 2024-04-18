package com.project.Retil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/api/data")
    public String test() {
        return "Hello, world!";
    }
}
