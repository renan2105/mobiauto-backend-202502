package com.renan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.service.LojaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LojaController.class)
@AutoConfigureMockMvc(addFilters = false)
class LojaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LojaService lojaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarLoja() throws Exception {
        LojaRequestDTO req = new LojaRequestDTO();
        req.setNome("Minha Loja");

        LojaResponseDTO resp = new LojaResponseDTO(UUID.randomUUID(), "Minha Loja");


        Mockito.when(lojaService.criar(any(LojaRequestDTO.class))).thenReturn(resp);

        mvc.perform(post("/api/lojas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Minha Loja"));
    }

    @Test
    void deveListarLojas() throws Exception {
        LojaResponseDTO resp = new LojaResponseDTO(UUID.randomUUID(), "Loja Teste");


        Mockito.when(lojaService.listar()).thenReturn(List.of(resp));

        mvc.perform(get("/api/lojas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Loja Teste"));
    }

    @Test
    void deveBuscarLojaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        LojaResponseDTO resp = new LojaResponseDTO(id, "Loja Buscada");


        Mockito.when(lojaService.buscarPorId(eq(id))).thenReturn(resp);

        mvc.perform(get("/api/lojas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Loja Buscada"));
    }

    @Test
    void deveAtualizarLoja() throws Exception {
        UUID id = UUID.randomUUID();
        LojaRequestDTO req = new LojaRequestDTO();
        req.setNome("Loja Atualizada");

        LojaResponseDTO resp = new LojaResponseDTO(id, "Loja Atualizada");

        Mockito.when(lojaService.atualizar(eq(id), any(LojaRequestDTO.class))).thenReturn(resp);

        mvc.perform(put("/api/lojas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Loja Atualizada"));
    }

    @Test
    void deveDeletarLoja() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/lojas/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(lojaService).deletar(eq(id));
    }
}
