package com.renan.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;

public class RevendaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Cnpj é obrigatório")
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
