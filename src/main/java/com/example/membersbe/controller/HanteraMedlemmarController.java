package com.example.membersbe.controller;


import com.example.membersbe.dto.RequestResponse;
import com.example.membersbe.entity.Medlem;
import com.example.membersbe.service.HanteraMedlemmarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class HanteraMedlemmarController {

    private final HanteraMedlemmarService hanteraMedlemmarService;

    public HanteraMedlemmarController(HanteraMedlemmarService hanteraMedlemmarService) {
        this.hanteraMedlemmarService = hanteraMedlemmarService;
    }

    @PostMapping("/auth/registrera")
    public ResponseEntity<RequestResponse> registreraMedlem(@RequestBody RequestResponse request) {
        return ResponseEntity.ok(hanteraMedlemmarService.registreraMedlem(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> loggaIn(@RequestBody RequestResponse request) {
        return ResponseEntity.ok(hanteraMedlemmarService.inLoggning(request));
    }

    @PostMapping("/auth/uppdatera-token")
    public ResponseEntity<RequestResponse> uppdateraJwtToken(@RequestBody RequestResponse request) {
        return ResponseEntity.ok(hanteraMedlemmarService.uppdateraToken(request));
    }

    @GetMapping("/admin/hamta-alla-medlemmar")
    public ResponseEntity<RequestResponse> hamtaAllaMedlemmar() {
        return ResponseEntity.ok(hanteraMedlemmarService.hamtaAllaMedlemmar());
    }

    @GetMapping("/admin/hamta-medlem/{id}")
    public ResponseEntity<RequestResponse> hamtaMedlem(@PathVariable UUID id) {
        return ResponseEntity.ok(hanteraMedlemmarService.hamtaMedlemMedId(id));
    }

    @PutMapping("/admin/uppdatera/{id}")
    public ResponseEntity<RequestResponse> uppdateraMedlem(@PathVariable UUID id, @RequestBody Medlem medlem) {
        return ResponseEntity.ok(hanteraMedlemmarService.uppdateraMedlem(id, medlem));
    }

    @GetMapping("/admin/hamta-profil")
    public ResponseEntity<RequestResponse> hamtaMinInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String epost = authentication.getName();
        RequestResponse response = hanteraMedlemmarService.hamtaMinInfo(epost);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/admin/radera-medlem/{id}")
    public ResponseEntity<RequestResponse> raderaMedlem(@PathVariable UUID id) {
        return ResponseEntity.ok(hanteraMedlemmarService.raderaMedlem(id));
    }
}
