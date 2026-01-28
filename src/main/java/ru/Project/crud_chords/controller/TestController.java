package ru.Project.crud_chords.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Приложение работает! Время: " + java.time.LocalDateTime.now();
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}