package com.nagi.spring_security_latest.controller;

import com.nagi.spring_security_latest.dto.AuthRequest;
import com.nagi.spring_security_latest.entity.UserInfo;
import com.nagi.spring_security_latest.repository.UserInfoRepository;
import com.nagi.spring_security_latest.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    private Map<String, String> refreshTokenStore = new HashMap<>(); // simulate DB

    @PostMapping("/new")
    public String createUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User created successfully";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateToken(authRequest.getUserName(), 15 * 60 * 1000, "access"); // 15 min
            String refreshToken = jwtService.generateToken(authRequest.getUserName(), 30L * 24 * 60 * 60 * 1000, "refresh"); // 30 days

            refreshTokenStore.put(authRequest.getUserName(), refreshToken); // Store refresh token

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));
        }
        return null;
    }
        @PostMapping("/refresh-token")
        public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {

            String tokenType = jwtService.extractTokenType(refreshToken);
            if (!"refresh".equals(tokenType)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token type");
            }

            String username = jwtService.extractUserName(refreshToken);

            if (!refreshToken.equals(refreshTokenStore.get(username))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

            if (!jwtService.isTokenValid(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
            }

            String newAccessToken = jwtService.generateToken(username, 15 * 60 * 1000, "access");
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(@RequestParam String username) {
            refreshTokenStore.remove(username); // Invalidate refresh token
            return ResponseEntity.ok("Logged out successfully");
        }
    }

