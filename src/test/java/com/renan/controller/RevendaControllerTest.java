package com.renan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.dto.RevendaRequestDTO;
import com.renan.dto.RevendaResponseDTO;
import com.renan.service.RevendaService;
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

@WebMvcTest(RevendaController.class)
@AutoConfigureMockMvc(addFilters = false)
class RevendaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RevendaService revendaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarRevenda() throws Exception {
        RevendaRequestDTO req = new RevendaRequestDTO();
        req.setNome("Revenda Teste");
        req.setCnpj("12.090.558/0001-94");

        RevendaResponseDTO resp = new RevendaResponseDTO(UUID.randomUUID(), "Revenda Teste", "12.090.558/0001-94");


        Mockito.when(revendaService.criar(any(RevendaRequestDTO.class))).thenReturn(resp);

        mvc.perform(post("/api/revendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Revenda Teste"));
    }

    @Test
    void deveListarRevendas() throws Exception {
        RevendaResponseDTO resp = new RevendaResponseDTO(UUID.randomUUID(), "Revenda Listada", "78.536.803/0001-52");

        Mockito.when(revendaService.listar()).thenReturn(List.of(resp));

        mvc.perform(get("/api/revendas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Revenda Listada"));
    }

    @Test
    void deveBuscarRevendaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        RevendaResponseDTO resp = new RevendaResponseDTO(id, "Revenda Buscada", "85.563.778/0001-61");


        Mockito.when(revendaService.buscarPorId(eq(id))).thenReturn(resp);

        mvc.perform(get("/api/revendas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Revenda Buscada"));
    }

    @Test
    void deveAtualizarRevenda() throws Exception {
        UUID id = UUID.randomUUID();
        RevendaRequestDTO req = new RevendaRequestDTO();
        req.setNome("Revenda Atualizada");
        req.setCnpj("61.503.434/0001-07");

        RevendaResponseDTO resp = new RevendaResponseDTO(id, "Revenda Atualizada", "61.503.434/0001-07");

        Mockito.when(revendaService.atualizar(eq(id), any(RevendaRequestDTO.class))).thenReturn(resp);

        mvc.perform(put("/api/revendas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Revenda Atualizada"));
    }

    @Test
    void deveDeletarRevenda() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/revendas/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(revendaService).deletar(eq(id));
    }
}
