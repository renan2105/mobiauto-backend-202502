package com.renan.domain;

import com.renan.domain.embedded.Cliente;
import com.renan.domain.embedded.Veiculo;
import com.renan.domain.enums.StatusOportunidade;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Document(collection = "oportunidades")
public class Oportunidade {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    private UUID idLoja;

    private StatusOportunidade status = StatusOportunidade.NOVO;

    private String motivoConclusao;

    @NotNull
    @Valid
    private Cliente cliente;

    @NotNull
    @Valid
    private Veiculo veiculo;

    public Oportunidade() {}

    public Oportunidade(UUID idLoja, Cliente cliente, Veiculo veiculo) {
        this.idLoja = idLoja;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.status = StatusOportunidade.NOVO;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdLoja() { return idLoja; }
    public void setIdLoja(UUID idLoja) { this.idLoja = idLoja; }

    public StatusOportunidade getStatus() { return status; }
    public void setStatus(StatusOportunidade status) { this.status = status; }

    public String getMotivoConclusao() { return motivoConclusao; }
    public void setMotivoConclusao(String motivoConclusao) { this.motivoConclusao = motivoConclusao; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
}
