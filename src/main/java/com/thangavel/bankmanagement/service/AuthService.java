package com.thangavel.bankmanagement.service;

import com.thangavel.bankmanagement.dto.request.LoginRequest;
import com.thangavel.bankmanagement.dto.request.RefreshTokenRequest;
import com.thangavel.bankmanagement.dto.request.RegisterRequest;
import com.thangavel.bankmanagement.dto.response.LoginResponse;
import com.thangavel.bankmanagement.dto.response.RefreshTokenResponse;
import com.thangavel.bankmanagement.dto.response.RegisterResponse;
import com.thangavel.bankmanagement.entity.User;
import com.thangavel.bankmanagement.repository.UserRepository;
import com.thangavel.bankmanagement.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;


    public RegisterResponse signUp(RegisterRequest reqRes) {
        User user = User.builder()
                .email(reqRes.getEmail())
                .firstname(reqRes.getFirstname())
                .lastname(reqRes.getLastname())
                .password(passwordEncoder.encode(reqRes.getPassword()))
                .userRole(Role.USER)
                .build();
        User result = repository.save(user);

        String jwtToken = service.generateToken(result);
        String refreshToken = service.refreshToken(new HashMap<>(), result);

        return new RegisterResponse(
                jwtToken,
                refreshToken,
                Role.USER
        );
    }

    public User getUser(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authManager.authenticate(authentication);

        User user = getUser(request.getEmail());
        String jwtToken = service.generateToken(user);
        String refreshToken = service.refreshToken(new HashMap<>(), user);

        return new LoginResponse(
                jwtToken,
                refreshToken,
                Role.USER
        );
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws Exception {
        String email = service.extractUsername(request.getRefreshToken());
        User user = getUser(email);

        if (!service.validateToken(request.getAuthToken(), user)) throw new Exception("not invalid token");

        String jwtToken = service.generateToken(user);
        return new RefreshTokenResponse(
                jwtToken,
                request.getRefreshToken()
        );
    }
}
