package com.quitq.controller;

import com.quitq.dto.LoginRequestDTO;
import com.quitq.dto.LoginResponseDTO;
import com.quitq.model.User;
import com.quitq.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @GetMapping("/login")
    public ResponseEntity<?>getLoginToken(Principal principal){

        String loggedInUser=principal.getName();
        Map<String,String>response=new HashMap<>();
        response.put("token", jwtUtil.generateToken(loggedInUser));
        return ResponseEntity.ok(response);
    }


}
