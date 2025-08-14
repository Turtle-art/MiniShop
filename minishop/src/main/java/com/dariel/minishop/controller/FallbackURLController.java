package com.dariel.minishop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackURLController {
    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId) {
        return "Payment successful! Session ID: " + sessionId;
    }

    @GetMapping("/cancel")
    public String paymentCanceled() {
        return "Payment canceled!";
    }
}
