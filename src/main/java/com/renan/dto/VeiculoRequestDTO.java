package com.renan.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VeiculoRequestDTO {

    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotBlank(message = "A versão é obrigatória")
    private String versao;

    @NotNull(message = "O ano é obrigatório")
    private Integer ano;

    public VeiculoRequestDTO() {}

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
}
