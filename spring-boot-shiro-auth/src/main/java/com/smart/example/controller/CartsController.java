package com.smart.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartsController {

    @GetMapping("/list")
    public List<String> carts() {
        return new ArrayList<>();
    }
}
