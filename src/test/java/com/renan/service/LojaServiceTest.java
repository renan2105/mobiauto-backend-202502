package com.renan.service;

import com.renan.domain.Loja;
import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.repository.LojaRepository;
import com.renan.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LojaServiceTest {

    private LojaRepository repository;
    private LojaService service;
    private SecurityUtils securityUtils;

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
}
