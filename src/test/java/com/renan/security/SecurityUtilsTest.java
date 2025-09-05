package com.renan.security;

import com.renan.domain.PerfilLoja;
import com.renan.domain.Usuario;
import com.renan.domain.enums.Cargo;
import com.renan.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityUtilsTest {

    private UsuarioRepository usuarioRepository;
    private SecurityUtils securityUtils;

    @BeforeEach
    void setup() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        securityUtils = new SecurityUtils(usuarioRepository);
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveRetornarUsuarioAutenticado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@mobiauto.com");

        when(usuarioRepository.findByEmail("teste@mobiauto.com"))
                .thenReturn(Optional.of(usuario));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("teste@mobiauto.com", null, Collections.emptyList())
        );

        Usuario current = securityUtils.getCurrentUser();
        assertEquals("teste@mobiauto.com", current.getEmail());
    }

    @Test
    void devePermitirAdministradorGerenciarLoja() {
        Usuario admin = new Usuario();
        admin.setEmail("admin@mobiauto.com");
        admin.setCargo(Cargo.ADMINISTRADOR);

        when(usuarioRepository.findByEmail("admin@mobiauto.com")).thenReturn(Optional.of(admin));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin@mobiauto.com", null, Collections.emptyList())
        );

        assertDoesNotThrow(() -> securityUtils.checkManagePermission(UUID.randomUUID()));
    }

    @Test
    void deveNegarAssistenteGerenciarLoja() {
        UUID lojaId = UUID.randomUUID();

        Usuario user = new Usuario();
        user.setEmail("assistente@mobiauto.com");
        user.setPerfis(Collections.singletonList(new PerfilLoja(lojaId, Cargo.ASSISTENTE)));

        when(usuarioRepository.findByEmail("assistente@mobiauto.com")).thenReturn(Optional.of(user));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("assistente@mobiauto.com", null, Collections.emptyList())
        );

        assertThrows(AccessDeniedException.class, () -> securityUtils.checkManagePermission(lojaId));
    }
}
