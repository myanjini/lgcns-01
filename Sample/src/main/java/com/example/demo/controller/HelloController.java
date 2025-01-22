package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // @RequestMapping("/")
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    // @GetMapping(value = "/")
    @GetMapping("/")
    public String hello() {
        return "Hello World!!! ^^";
    }
}
