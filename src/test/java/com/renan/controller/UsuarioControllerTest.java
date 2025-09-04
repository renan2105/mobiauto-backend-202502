package com.renan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.domain.enums.Cargo;
import com.renan.dto.UsuarioRequestDTO;
import com.renan.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarUsuarios() throws Exception {
        Mockito.when(service.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveCriarUsuario() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("renan");
        dto.setEmail("renan@email.com");
        dto.setSenha("senha123");
        dto.setCargo(Cargo.ASSISTENTE);

        UUID id = UUID.randomUUID();
        Mockito.when(service.criar(any(UsuarioRequestDTO.class)))
                .thenReturn(new com.renan.dto.UsuarioResponseDTO(id, dto.getNome(), dto.getEmail(), dto.getCargo()));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("renan"))
                .andExpect(jsonPath("$.email").value("renan@email.com"));
    }
}
