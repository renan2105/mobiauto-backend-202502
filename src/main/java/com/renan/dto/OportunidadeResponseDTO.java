package com.renan.dto;

import com.renan.domain.enums.StatusOportunidade;

import java.util.UUID;

public class OportunidadeResponseDTO {

    private UUID id;
    private UUID idLoja;
    private StatusOportunidade status;
    private String motivoConclusao;
    private ClienteResponseDTO cliente;
    private VeiculoResponseDTO veiculo;

    public OportunidadeResponseDTO() {}

    public OportunidadeResponseDTO(UUID id, UUID idLoja, StatusOportunidade status,
                                   String motivoConclusao, ClienteResponseDTO cliente, VeiculoResponseDTO veiculo) {
        this.id = id;
        this.idLoja = idLoja;
        this.status = status;
        this.motivoConclusao = motivoConclusao;
        this.cliente = cliente;
        this.veiculo = veiculo;
    }

    public UUID getId() { return id; }
    public UUID getIdLoja() { return idLoja; }
    public StatusOportunidade getStatus() { return status; }
    public String getMotivoConclusao() { return motivoConclusao; }
    public ClienteResponseDTO getCliente() { return cliente; }
    public VeiculoResponseDTO getVeiculo() { return veiculo; }
}
