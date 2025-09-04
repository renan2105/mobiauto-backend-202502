package com.renan.service;

import com.renan.domain.Loja;
import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.LojaRepository;
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

class LojaServiceTest {

    @Mock
    private LojaRepository repository;

    @InjectMocks
    private LojaService service;

    private Loja loja;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        loja = new Loja();
        loja.setId(UUID.randomUUID());
        loja.setNome("Loja Central");
    }

    @Test
    void deveBuscarLojaPorId() {
        when(repository.findById(loja.getId())).thenReturn(Optional.of(loja));

        LojaResponseDTO result = service.buscarPorId(loja.getId());

        assertNotNull(result);
        assertEquals(loja.getNome(), result.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarLoja() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(id));
    }

    @Test
    void deveCriarLoja() {
        LojaRequestDTO dto = new LojaRequestDTO();
        dto.setNome("Loja Nova");

        when(repository.save(any(Loja.class))).thenAnswer(inv -> inv.getArgument(0));

        LojaResponseDTO result = service.criar(dto);

        assertNotNull(result.getId());
        assertEquals(dto.getNome(), result.getNome());
    }

    @Test
    void deveAtualizarLoja() {
        LojaRequestDTO dto = new LojaRequestDTO();
        dto.setNome("Loja Atualizada");

        when(repository.findById(loja.getId())).thenReturn(Optional.of(loja));
        when(repository.save(any(Loja.class))).thenAnswer(inv -> inv.getArgument(0));

        LojaResponseDTO result = service.atualizar(loja.getId(), dto);

        assertEquals(dto.getNome(), result.getNome());
    }

    @Test
    void deveDeletarLoja() {
        when(repository.existsById(loja.getId())).thenReturn(true);
        doNothing().when(repository).deleteById(loja.getId());

        assertDoesNotThrow(() -> service.deletar(loja.getId()));

        verify(repository, times(1)).deleteById(loja.getId());
    }

    @Test
    void deveLancarExcecaoAoDeletarLojaNaoExistente() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(id));
    }
}