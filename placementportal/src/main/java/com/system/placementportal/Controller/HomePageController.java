package com.system.placementportal.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {

    @GetMapping("/")
    public String home() {
        return "Placement Portal Backend is Running Successfully!";
    }
}