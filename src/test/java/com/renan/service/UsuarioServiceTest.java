package com.renan.service;

import com.renan.domain.Usuario;
import com.renan.domain.enums.Cargo;
import com.renan.dto.UsuarioRequestDTO;
import com.renan.dto.UsuarioResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("renan");
        usuario.setEmail("renan@email.com");
        usuario.setSenha("senha123");
        usuario.setCargo(Cargo.ADMINISTRADOR);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO result = service.buscarPorId(usuario.getId());

        assertNotNull(result);
        assertEquals(usuario.getNome(), result.getNome());
        assertEquals(usuario.getEmail(), result.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarUsuario() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(id));
    }

    @Test
    void deveCriarUsuario() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("renan");
        dto.setEmail("renan@email.com");
        dto.setSenha("senha123");
        dto.setCargo(Cargo.GERENTE);

        when(repository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        UsuarioResponseDTO result = service.criar(dto);

        assertNotNull(result.getId());
        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getEmail(), result.getEmail());
    }
}
