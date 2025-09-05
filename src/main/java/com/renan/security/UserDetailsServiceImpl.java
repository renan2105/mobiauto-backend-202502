package com.renan.security;

import com.renan.domain.Usuario;
import com.renan.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        String role = user.getCargo() != null ? user.getCargo().name() : "ASSISTENTE";
        return new User(user.getEmail(), user.getSenha(), Collections.singletonList(new SimpleGrantedAuthority(role)));
    }
}
