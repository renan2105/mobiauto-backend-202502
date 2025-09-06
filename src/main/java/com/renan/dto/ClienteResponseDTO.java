package com.renan.dto;

public class ClienteResponseDTO {
    private String nome;
    private String email;
    private String telefone;

    public ClienteResponseDTO() {}

    public ClienteResponseDTO(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
}
