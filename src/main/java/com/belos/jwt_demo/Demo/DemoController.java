package com.belos.jwt_demo.Demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping
    public String welcome()
    {
        return "Welcome from secure endpoint";
    }
}