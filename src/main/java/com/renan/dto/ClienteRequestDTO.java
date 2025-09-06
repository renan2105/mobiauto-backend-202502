package com.renan.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ClienteRequestDTO {

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nome;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail do cliente é obrigatório")
    private String email;

    @NotBlank(message = "O telefone do cliente é obrigatório")
    private String telefone;

    public ClienteRequestDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
