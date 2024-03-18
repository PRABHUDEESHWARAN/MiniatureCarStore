package com.project.carstore.security;

import com.project.carstore.cart.CartException;
import com.project.carstore.exceptions.CustomerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO request) throws CustomerException, CartException {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateDTO> validateUser(@RequestParam("token") String token) {

        return ResponseEntity.ok(authenticationService.isValidToken(token));

    }
}

