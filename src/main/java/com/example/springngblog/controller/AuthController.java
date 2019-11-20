package com.example.springngblog.controller;

import com.example.springngblog.service.AuthenticationResponse;
import com.example.springngblog.dto.LoginRequest;
import com.example.springngblog.dto.RegisterRequest;
import com.example.springngblog.dto.UserDto;
import com.example.springngblog.model.User;
import com.example.springngblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @GetMapping("/getuser/{username}")
    public UserDto getAllUser(@PathVariable String username)
    {
        return authService.getAllUser(username);

    }
    @PutMapping("/update/user/{username}/{email}/{password}/{id}")
    public User updateUser(@PathVariable String username, @PathVariable String email, @PathVariable String password, @PathVariable Long id) {
        return authService.updateUser(username,email,password,id);

    }
}
