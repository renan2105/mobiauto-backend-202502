package com.renan.domain.embedded;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Veiculo {

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private String versao;

    @NotNull
    private Integer ano;

    public Veiculo() {}

    public Veiculo(String marca, String modelo, String versao, Integer ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.versao = versao;
        this.ano = ano;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
}
