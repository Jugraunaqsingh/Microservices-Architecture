package com.example.shipping.controller;

import com.example.shipping.service.ServiceToggle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ServiceToggle toggle;
    public AdminController(ServiceToggle toggle) { this.toggle = toggle; }

    @PostMapping("/enable")
    public ResponseEntity<?> enable() { toggle.enable(); return ResponseEntity.ok(Map.of("enabled", true)); }

    @PostMapping("/disable")
    public ResponseEntity<?> disable() { toggle.disable(); return ResponseEntity.ok(Map.of("enabled", false)); }

    @GetMapping("/status")
    public Map<String, Object> status() { return Map.of("enabled", toggle.isEnabled()); }
}
