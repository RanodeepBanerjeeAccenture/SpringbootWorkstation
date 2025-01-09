package com.accenture.usermanagementapp.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class GlobalExceptionHandler {
    @RequestMapping("/error")
    public String handleError() {
        // Return a custom error page, e.g., "error.html"
        return "error";
    }
}