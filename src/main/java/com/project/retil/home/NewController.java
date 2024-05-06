package com.project.Retil.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NewController {

    @GetMapping("/")
    public String index() {
        return "첫 화면";
    }

    @GetMapping("/api/home")
    public String home() {
        return "Hello World!";
    }

}