package com.renan.dto;

import java.util.UUID;

public class LojaResponseDTO {
    private UUID id;
    private String nome;

    public LojaResponseDTO(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
