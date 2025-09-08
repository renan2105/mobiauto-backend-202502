package com.renan.service;

import com.renan.domain.Loja;
import com.renan.domain.Revenda;
import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.dto.RevendaRequestDTO;
import com.renan.dto.RevendaResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.RevendaRepository;
import com.renan.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RevendaServiceTest {

    private RevendaRepository repository;
    private RevendaService service;
    private SecurityUtils securityUtils;

    private Revenda revenda;
    private RevendaRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        repository = mock(RevendaRepository.class);
        securityUtils = mock(SecurityUtils.class);
        try {
            service = new RevendaService(repository, securityUtils);
        } catch (Throwable t) {
            try {
                service = new RevendaService(repository, null);
            } catch (Throwable ex) {
                service = null;
            }
        }
        
        revenda = new Revenda();
        revenda.setId(UUID.randomUUID());
        revenda.setNome("Revenda Teste");

        requestDTO = new RevendaRequestDTO();
        requestDTO.setNome("Revenda Teste");
    }

    @Test
    void deveCriarRevenda() {
        RevendaRequestDTO dto = new RevendaRequestDTO();
        dto.setNome("Revenda Astra");
        dto.setCnpj("88.846.977/0001-75");

        when(repository.save(any(Revenda.class))).thenAnswer(inv -> inv.getArgument(0));

        RevendaResponseDTO response = service.criar(dto);

        assertEquals("Revenda Astra", response.getNome());
        assertEquals("88.846.977/0001-75", response.getCnpj());
        assertNotNull(response.getId());
    }

    @Test
    void deveBuscarRevendaPorId() {
        UUID id = UUID.randomUUID();
        Revenda revenda = new Revenda();
        revenda.setId(id);
        revenda.setNome("Revenda Fiesta");
        revenda.setCnpj("59.702.985/0001-40");

        when(repository.findById(id)).thenReturn(Optional.of(revenda));

        RevendaResponseDTO response = service.buscarPorId(id);

        assertEquals("Revenda Fiesta", response.getNome());
        assertEquals("59.702.985/0001-40", response.getCnpj());
    }


    @Test
    void deveListarRevendas() {
        when(repository.findAll()).thenReturn(List.of(revenda));

        List<RevendaResponseDTO> result = service.listar();

        assertEquals(1, result.size());
    }
    
    @Test
    void deveAtualizarRevenda() {
        when(repository.findById(revenda.getId())).thenReturn(Optional.of(revenda));
        when(repository.save(any(Revenda.class))).thenReturn(revenda);

        RevendaRequestDTO updateDTO = new RevendaRequestDTO();
        updateDTO.setNome("Revenda Atualizada");

        RevendaResponseDTO result = service.atualizar(revenda.getId(), updateDTO);

        assertEquals("Revenda Atualizada", result.getNome());
        verify(repository).save(any(Revenda.class));
    }
    @Test
    void deveLancarExcecaoAoBuscarInexistente() {
        when(repository.findById(revenda.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(revenda.getId()));
    }

    @Test
    void deveDeletarRevenda() {
        when(repository.existsById(revenda.getId())).thenReturn(true);

        service.deletar(revenda.getId());

        verify(repository).deleteById(revenda.getId());
    }

    @Test
    void deveLancarExcecaoAoDeletarInexistente() {
        when(repository.existsById(revenda.getId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(revenda.getId()));
    }
}
