package com.renan.controller;

import com.renan.domain.Usuario;
import com.renan.dto.AuthRequest;
import com.renan.dto.AuthResponse;
import com.renan.repository.UsuarioRepository;
import com.renan.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UsuarioRepository usuarioRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        String token = tokenProvider.createToken(request.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElse(null);
        return ResponseEntity.ok(new AuthResponse(token, usuario != null ? usuario.getId() : null, usuario != null ? usuario.getEmail() : null));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(java.util.UUID.randomUUID());
        usuario.setNome(request.getEmail());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setCargo(com.renan.domain.enums.Cargo.ADMINISTRADOR);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Administrador criado");
    }
}
