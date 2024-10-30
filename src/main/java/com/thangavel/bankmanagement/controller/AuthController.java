package com.thangavel.bankmanagement.controller;

import com.thangavel.bankmanagement.dto.request.LoginRequest;
import com.thangavel.bankmanagement.dto.request.RefreshTokenRequest;
import com.thangavel.bankmanagement.dto.request.RegisterRequest;
import com.thangavel.bankmanagement.dto.response.LoginResponse;
import com.thangavel.bankmanagement.dto.response.RefreshTokenResponse;
import com.thangavel.bankmanagement.dto.response.RegisterResponse;
import com.thangavel.bankmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signUp(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws Exception {
        return ResponseEntity.ok(service.refreshToken(request));
    }
}
