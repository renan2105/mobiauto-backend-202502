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
