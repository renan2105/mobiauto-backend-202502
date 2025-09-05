package com.renan.dto;

import javax.validation.constraints.NotBlank;

public class LojaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
