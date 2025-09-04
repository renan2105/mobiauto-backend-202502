package com.renan.dto;

import com.renan.domain.enums.Cargo;

import java.util.UUID;

public class UsuarioResponseDTO {

    private UUID id;
    private String nome;
    private String email;
    private Cargo cargo;

    public UsuarioResponseDTO(UUID id, String nome, String email, Cargo cargo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Cargo getCargo() {
        return cargo;
    }
}
