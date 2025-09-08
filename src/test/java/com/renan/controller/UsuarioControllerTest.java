package com.renan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.domain.enums.Cargo;
import com.renan.dto.UsuarioRequestDTO;
import com.renan.dto.UsuarioResponseDTO;
import com.renan.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarUsuario() throws Exception {
        UsuarioRequestDTO req = new UsuarioRequestDTO();
        req.setEmail("user@test.com");
        req.setSenha("123456");
        req.setNome("teste");
        req.setCargo(Cargo.ASSISTENTE);

        UsuarioResponseDTO resp = new UsuarioResponseDTO(UUID.randomUUID(),
                "teste",
                "user@test.com",
                Cargo.ASSISTENTE);

        Mockito.when(usuarioService.criar(any(UsuarioRequestDTO.class))).thenReturn(resp);

        mvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("user@test.com"));
    }

    @Test
    void deveListarUsuarios() throws Exception {
        UsuarioResponseDTO resp = new UsuarioResponseDTO(UUID.randomUUID(),
                "teste",
                "user2@test.com",
                Cargo.ASSISTENTE);

        Mockito.when(usuarioService.listar()).thenReturn(List.of(resp));

        mvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("user2@test.com"));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioResponseDTO resp = new UsuarioResponseDTO(id,
                "teste",
                "buscar@test.com",
                Cargo.ASSISTENTE);

        Mockito.when(usuarioService.buscarPorId(eq(id))).thenReturn(resp);

        mvc.perform(get("/api/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("buscar@test.com"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioRequestDTO req = new UsuarioRequestDTO();
        req.setEmail("atualizado@test.com");
        req.setSenha("123456");
        req.setNome("teste");
        req.setCargo(Cargo.ASSISTENTE);

        UsuarioResponseDTO resp = new UsuarioResponseDTO(id,
                "teste",
                "atualizado@test.com",
                Cargo.ASSISTENTE);

        Mockito.when(usuarioService.atualizar(eq(id), any(UsuarioRequestDTO.class))).thenReturn(resp);

        mvc.perform(put("/api/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("atualizado@test.com"));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/usuarios/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(usuarioService).deletar(eq(id));
    }
}
