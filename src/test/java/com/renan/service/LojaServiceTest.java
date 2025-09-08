package com.renan.service;

import com.renan.domain.Loja;
import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.LojaRepository;
import com.renan.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LojaServiceTest {

    private LojaRepository repository;
    private LojaService service;
    private SecurityUtils securityUtils;

    private Loja loja;
    private LojaRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        repository = mock(LojaRepository.class);
        securityUtils = mock(SecurityUtils.class);
        try {
            service = new LojaService(repository, securityUtils);
        } catch (Throwable t) {
            try {
                service = new LojaService(repository, null);
            } catch (Throwable ex) {
                service = null;
            }
        }

        loja = new Loja();
        loja.setId(UUID.randomUUID());
        loja.setNome("Loja Teste");

        requestDTO = new LojaRequestDTO();
        requestDTO.setNome("Loja Teste");
    }

    @Test
    void deveCriarLoja() {
        LojaRequestDTO dto = new LojaRequestDTO();
        dto.setNome("Loja Central");

        when(repository.save(any(Loja.class))).thenAnswer(inv -> inv.getArgument(0));

        LojaResponseDTO response = service.criar(dto);

        assertEquals("Loja Central", response.getNome());
        assertNotNull(response.getId());
    }

    @Test
    void deveBuscarLojaPorId() {
        UUID id = UUID.randomUUID();
        Loja loja = new Loja();
        loja.setId(id);
        loja.setNome("Filial 1");

        when(repository.findById(id)).thenReturn(Optional.of(loja));

        LojaResponseDTO response = service.buscarPorId(id);

        assertEquals("Filial 1", response.getNome());
    }

    @Test
    void deveListarLojas() {
        when(repository.findAll()).thenReturn(List.of(loja));

        List<LojaResponseDTO> result = service.listar();

        assertEquals(1, result.size());
        assertEquals("Loja Teste", result.get(0).getNome());
    }

    @Test
    void deveAtualizarLoja() {
        when(repository.findById(loja.getId())).thenReturn(Optional.of(loja));
        when(repository.save(any(Loja.class))).thenReturn(loja);

        LojaRequestDTO updateDTO = new LojaRequestDTO();
        updateDTO.setNome("Loja Atualizada");

        LojaResponseDTO result = service.atualizar(loja.getId(), updateDTO);

        assertEquals("Loja Atualizada", result.getNome());
        verify(repository).save(any(Loja.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarLojaInexistente() {
        when(repository.findById(loja.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar(loja.getId(), new LojaRequestDTO()));
    }

    @Test
    void deveDeletarLoja() {
        when(repository.existsById(any())).thenReturn(true);


        service.deletar(loja.getId());

        verify(repository).deleteById(loja.getId());
    }

    @Test
    void deveLancarExcecaoAoDeletarLojaInexistente() {
        when(repository.existsById(any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deletar(loja.getId()));
    }
}
