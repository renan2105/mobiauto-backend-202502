package com.renan.dto;

import java.util.UUID;

public class RevendaResponseDTO {
    private UUID id;
    private String nome;
    private String cnpj;

    public RevendaResponseDTO(UUID id, String nome, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
