package com.taskmanagement.Controller;



import com.taskmanagement.Dto.LoginRequestDTO;
import com.taskmanagement.Dto.LoginResponseDTO;
import com.taskmanagement.Security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {

        // step 1 - authenticate user with email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        // step 2 - get user details from authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // step 3 - extract role
        String role = userDetails.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        // step 4 - generate token
        String token = jwtUtil.generateToken(userDetails.getUsername(), role);

        // step 5 - return token + role + email
        return ResponseEntity.ok(new LoginResponseDTO(token, role, userDetails.getUsername()));
    }
}
