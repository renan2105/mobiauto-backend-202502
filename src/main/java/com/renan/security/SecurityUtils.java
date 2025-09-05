package com.renan.security;

import com.renan.domain.Usuario;
import com.renan.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    private final UsuarioRepository usuarioRepository;

    public SecurityUtils(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String email = (String) principal;
            return usuarioRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    public void checkManagePermission(UUID lojaId) {
        Usuario user = getCurrentUser();
        if (user == null) {
            throw new AccessDeniedException("Usuário não autenticado");
        }
        if (user.getCargo() != null && user.getCargo().name().equals("ADMINISTRADOR")) {
            return;
        }
        boolean ok = user.getPerfis().stream()
                .anyMatch(p -> p.getLojaId().equals(lojaId) && (p.getCargo().name().equals("PROPRIETARIO") || p.getCargo().name().equals("GERENTE")));
        if (!ok) {
            throw new AccessDeniedException("Sem permissão para gerenciar essa loja");
        }
    }
}
