package com.newBooking.Controllers;

import com.newBooking.DTO.Authentication.AuthenticationRequestDTO;
import com.newBooking.domain.Security.JwtTokenProvider;
import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO requestDTO) {
        String username = requestDTO.getUsername();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,requestDTO.getPassword()));
        UserEntity user = userService.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        String token = jwtTokenProvider.createToken(username,user.getRoles());

        Map<Object,Object> response = new HashMap<>();
        response.put("username",username);
        response.put("token",token);

        return ResponseEntity.ok(response);

    }
}
