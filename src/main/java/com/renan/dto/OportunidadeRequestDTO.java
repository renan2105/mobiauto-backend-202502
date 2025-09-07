package com.renan.dto;

import com.renan.domain.enums.StatusOportunidade;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OportunidadeRequestDTO {

    @NotNull(message = "O id da loja é obrigatório")
    private UUID idLoja;

    private StatusOportunidade status = StatusOportunidade.NOVO;

    private String motivoConclusao;

    private UUID usuarioId;

    @NotNull(message = "Os dados do cliente são obrigatórios")
    @Valid
    private ClienteRequestDTO cliente;

    @NotNull(message = "Os dados do veículo são obrigatórios")
    @Valid
    private VeiculoRequestDTO veiculo;

    public OportunidadeRequestDTO() {}

    public UUID getIdLoja() { return idLoja; }
    public void setIdLoja(UUID idLoja) { this.idLoja = idLoja; }

    public StatusOportunidade getStatus() { return status; }
    public void setStatus(StatusOportunidade status) { this.status = status; }

    public String getMotivoConclusao() { return motivoConclusao; }
    public void setMotivoConclusao(String motivoConclusao) { this.motivoConclusao = motivoConclusao; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public ClienteRequestDTO getCliente() { return cliente; }
    public void setCliente(ClienteRequestDTO cliente) { this.cliente = cliente; }

    public VeiculoRequestDTO getVeiculo() { return veiculo; }
    public void setVeiculo(VeiculoRequestDTO veiculo) { this.veiculo = veiculo; }
}
