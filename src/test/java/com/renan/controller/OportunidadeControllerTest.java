package com.renan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.domain.Oportunidade;
import com.renan.domain.embedded.Cliente;
import com.renan.domain.embedded.Veiculo;
import com.renan.dto.ClienteRequestDTO;
import com.renan.dto.OportunidadeRequestDTO;
import com.renan.dto.OportunidadeResponseDTO;
import com.renan.dto.VeiculoRequestDTO;
import com.renan.service.OportunidadeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OportunidadeController.class)
@AutoConfigureMockMvc(addFilters = false)
class OportunidadeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OportunidadeService oportunidadeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarOportunidade() throws Exception {
        OportunidadeRequestDTO req = new OportunidadeRequestDTO();
        req.setVeiculo(new VeiculoRequestDTO("marca", "modelo", "versao", 2025));
        req.setCliente(new ClienteRequestDTO("nome", "email@teste.com", "telefone"));
        req.setIdLoja(UUID.randomUUID());

        Oportunidade resp = new Oportunidade();
        resp.setVeiculo(new Veiculo("marca", "modelo", "versao", 2025));
        resp.setCliente(new Cliente("Cliente Teste", "email@teste.com", "telefone"));
        resp.setIdLoja(UUID.randomUUID());
        resp.setId(UUID.randomUUID());

        Mockito.when(oportunidadeService.criar(any(), any())).thenReturn(resp);

        mvc.perform(post("/api/oportunidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente.nome").value("Cliente Teste"));
    }

    @Test
    void deveBuscarOportunidadePorId() throws Exception {
        UUID id = UUID.randomUUID();
        Oportunidade resp = new Oportunidade();
        resp.setVeiculo(new Veiculo("marca", "modelo", "versao", 2025));
        resp.setCliente(new Cliente("Cliente Buscado", "email@teste.com", "telefone"));
        resp.setIdLoja(UUID.randomUUID());
        resp.setId(id);

        Mockito.when(oportunidadeService.buscarPorId(eq(id))).thenReturn(Optional.of(resp));

        mvc.perform(get("/api/oportunidades/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.cliente.nome").value("Cliente Buscado"));
    }

    @Test
    void deveBuscarOportunidadesPorIdLoja() throws Exception {
        UUID idLoja = UUID.randomUUID();
        Oportunidade resp = new Oportunidade();
        resp.setVeiculo(new Veiculo("marca", "modelo", "versao", 2025));
        resp.setCliente(new Cliente("Cliente Loja", "email@teste.com", "telefone"));
        resp.setIdLoja(idLoja);
        resp.setId(UUID.randomUUID());

        Mockito.when(oportunidadeService.buscarPorIdLoja(eq(idLoja))).thenReturn(List.of(resp));

        mvc.perform(get("/api/oportunidades/loja/{idLoja}", idLoja))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idLoja").value(idLoja.toString()))
                .andExpect(jsonPath("$[0].cliente.nome").value("Cliente Loja"));
    }

    @Test
    void deveAtualizarOportunidade() throws Exception {
        UUID id = UUID.randomUUID();
        OportunidadeRequestDTO req = new OportunidadeRequestDTO();
        req.setVeiculo(new VeiculoRequestDTO("marca", "modelo", "versao", 2025));
        req.setCliente(new ClienteRequestDTO("nome", "email@teste.com", "telefone"));
        req.setIdLoja(UUID.randomUUID());

        Oportunidade resp = new Oportunidade();
        resp.setVeiculo(new Veiculo("marca", "modelo", "versao", 2025));
        resp.setCliente(new Cliente("Cliente Atualizado", "email@teste.com", "telefone"));
        resp.setIdLoja(UUID.randomUUID());
        resp.setId(id);

        Mockito.when(oportunidadeService.atualizar(any(), any(), any())).thenReturn(resp);

        mvc.perform(put("/api/oportunidades/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.cliente.nome").value("Cliente Atualizado"));
    }

    @Test
    void deveDeletarOportunidade() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/oportunidades/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(oportunidadeService).deletar(eq(id));
    }
}
