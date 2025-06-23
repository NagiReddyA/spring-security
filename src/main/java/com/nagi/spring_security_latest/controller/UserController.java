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
    private PasswordEncoder encoder;

    @PostMapping("/new")
    public String createUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User created successfully";
    }

    }

