package org.saini.blogrestapi.controller;

import org.saini.blogrestapi.payload.JWTResponseDto;
import org.saini.blogrestapi.payload.LoginDto;
import org.saini.blogrestapi.payload.RegisterDto;
import org.saini.blogrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
@PostMapping(value={"/login","/signin"})
    public ResponseEntity<JWTResponseDto> login(@RequestBody LoginDto loginDto){
        String token=authService.login(loginDto);
    JWTResponseDto jwtResponseDto=new JWTResponseDto();
    jwtResponseDto.setAccessToken(token);
        return ResponseEntity.ok(jwtResponseDto);
    }
@PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
        String response=authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
