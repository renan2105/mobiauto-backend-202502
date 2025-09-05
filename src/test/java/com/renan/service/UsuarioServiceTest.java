package com.renan.service;

import com.renan.domain.Usuario;
import com.renan.domain.enums.Cargo;
import com.renan.dto.UsuarioRequestDTO;
import com.renan.dto.UsuarioResponseDTO;
import com.renan.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository repository;
    private BCryptPasswordEncoder encoder;
    private UsuarioService service;

    @BeforeEach
    void setup() {
        repository = mock(UsuarioRepository.class);
        encoder = new BCryptPasswordEncoder();
        service = new UsuarioService(repository, encoder);
    }

    @Test
    void deveCriarUsuarioComSenhaCriptografada() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("Renan");
        dto.setEmail("renan@mobiauto.com");
        dto.setSenha("123456");
        dto.setCargo(Cargo.GERENTE);

        when(repository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        UsuarioResponseDTO response = service.criar(dto);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        Usuario salvo = captor.getValue();

        assertEquals("Renan", response.getNome());
        assertTrue(encoder.matches("123456", salvo.getSenha()));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Teste");

        when(repository.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.buscarPorId(id);

        assertEquals("Teste", response.getNome());
    }
}
