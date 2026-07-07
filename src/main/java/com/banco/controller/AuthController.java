package com.banco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.CreateUserRequestDTO;
import com.banco.dto.LoginRequestDTO;
import com.banco.dto.LoginResponseDTO;
import com.banco.dto.UserResponseDTO;
import com.banco.entity.User;
import com.banco.service.JwtService;
import com.banco.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "1. Autenticación", description = "Endpoints públicos para el registro e inicio de sesión de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario con email y contraseña, retornando un token JWT válido por 24 horas.")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                    )
        );

        User user = (User) authentication.getPrincipal();
        
        String jwtToken = jwtService.generateToken(user);

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(jwtToken)
                .build();
       
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una cuenta en el sistema con rol USER por defecto.")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody CreateUserRequestDTO request) {
        UserResponseDTO response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}