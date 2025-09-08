package com.renan.service;

import com.renan.domain.Usuario;
import com.renan.domain.enums.Cargo;
import com.renan.dto.UsuarioRequestDTO;
import com.renan.dto.UsuarioResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository repository;
    private BCryptPasswordEncoder encoder;
    private UsuarioService service;

    private Usuario usuario;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        repository = mock(UsuarioRepository.class);
        encoder = new BCryptPasswordEncoder();
        service = new UsuarioService(repository, encoder);

        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setEmail("teste@email.com");

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setEmail("teste@email.com");
        requestDTO.setNome("Teste");
        requestDTO.setSenha("123456");

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

    @Test
    void deveListarUsuarios() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseDTO> result = service.listar();

        assertEquals(1, result.size());
        assertEquals("teste@email.com", result.get(0).getEmail());
    }

    @Test
    void deveAtualizarUsuario() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioRequestDTO updateDTO = new UsuarioRequestDTO();
        updateDTO.setEmail("novo@email.com");
        updateDTO.setNome("Novo Nome");
        updateDTO.setSenha("123456");

        UsuarioResponseDTO result = service.atualizar(usuario.getId(), updateDTO);

        assertEquals("novo@email.com", result.getEmail());
        verify(repository).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar(usuario.getId(), requestDTO));
    }

    @Test
    void deveDeletarUsuario() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        service.deletar(usuario.getId());

        verify(repository).delete(usuario);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deletar(usuario.getId()));
    }
}
