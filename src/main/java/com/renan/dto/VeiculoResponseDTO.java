package com.renan.dto;

public class VeiculoResponseDTO {
    private String marca;
    private String modelo;
    private String versao;
    private Integer ano;

    public VeiculoResponseDTO() {}

    public VeiculoResponseDTO(String marca, String modelo, String versao, Integer ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.versao = versao;
        this.ano = ano;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getVersao() { return versao; }
    public Integer getAno() { return ano; }
}
