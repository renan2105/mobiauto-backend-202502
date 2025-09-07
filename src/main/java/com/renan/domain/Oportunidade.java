package com.renan.domain;

import com.renan.domain.embedded.Cliente;
import com.renan.domain.embedded.Veiculo;
import com.renan.domain.enums.StatusOportunidade;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "oportunidades")
public class Oportunidade {

    @Id
    private UUID id;

    private UUID idLoja;

    private StatusOportunidade status = StatusOportunidade.NOVO;

    private String motivoConclusao;

    private Cliente cliente;

    private Veiculo veiculo;

    @DBRef
    private Usuario usuarioAtribuido;

    private Date dataAtribuicao;

    private Date dataConclusao;


    public Oportunidade() {}


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

    public Usuario getUsuarioAtribuido() { return usuarioAtribuido; }
    public void setUsuarioAtribuido(Usuario usuarioAtribuido) {
        this.usuarioAtribuido = usuarioAtribuido;
        this.dataAtribuicao = new Date(); // atualiza sempre que atribuído
    }

    public Date getDataAtribuicao() { return dataAtribuicao; }

    public Date getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(Date dataConclusao) { this.dataConclusao = dataConclusao; }
}
