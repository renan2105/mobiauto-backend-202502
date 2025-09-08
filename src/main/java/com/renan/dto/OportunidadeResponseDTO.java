package com.renan.dto;

import com.renan.domain.enums.StatusOportunidade;

import java.util.Date;
import java.util.UUID;

public class OportunidadeResponseDTO {

    private UUID id;
    private UUID idLoja;
    private StatusOportunidade status;
    private String motivoConclusao;
    private ClienteResponseDTO cliente;
    private VeiculoResponseDTO veiculo;

    private UsuarioResponseDTO usuarioAtribuido;
    private Date dataAtribuicao;
    private Date dataConclusao;

    public OportunidadeResponseDTO() {}

    public OportunidadeResponseDTO(UUID id, UUID idLoja, StatusOportunidade status,
                                   String motivoConclusao, ClienteResponseDTO cliente,
                                   VeiculoResponseDTO veiculo, UsuarioResponseDTO usuarioAtribuido,
                                   Date dataAtribuicao, Date dataConclusao) {
        this.id = id;
        this.idLoja = idLoja;
        this.status = status;
        this.motivoConclusao = motivoConclusao;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.usuarioAtribuido = usuarioAtribuido;
        this.dataAtribuicao = dataAtribuicao;
        this.dataConclusao = dataConclusao;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIdLoja(UUID idLoja) {
        this.idLoja = idLoja;
    }

    public void setStatus(StatusOportunidade status) {
        this.status = status;
    }

    public void setMotivoConclusao(String motivoConclusao) {
        this.motivoConclusao = motivoConclusao;
    }

    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }

    public void setVeiculo(VeiculoResponseDTO veiculo) {
        this.veiculo = veiculo;
    }

    public void setUsuarioAtribuido(UsuarioResponseDTO usuarioAtribuido) {
        this.usuarioAtribuido = usuarioAtribuido;
    }

    public void setDataAtribuicao(Date dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public UUID getId() { return id; }
    public UUID getIdLoja() { return idLoja; }
    public StatusOportunidade getStatus() { return status; }
    public String getMotivoConclusao() { return motivoConclusao; }
    public ClienteResponseDTO getCliente() { return cliente; }
    public VeiculoResponseDTO getVeiculo() { return veiculo; }
    public UsuarioResponseDTO getUsuarioAtribuido() { return usuarioAtribuido; }
    public Date getDataAtribuicao() { return dataAtribuicao; }
    public Date getDataConclusao() { return dataConclusao; }
}
