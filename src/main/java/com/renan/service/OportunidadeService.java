package com.renan.service;

import com.renan.domain.Oportunidade;
import com.renan.domain.enums.StatusOportunidade;
import com.renan.repository.OportunidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;

    public OportunidadeService(OportunidadeRepository oportunidadeRepository) {
        this.oportunidadeRepository = oportunidadeRepository;
    }

    public Oportunidade criar(Oportunidade oportunidade) {
        return oportunidadeRepository.save(oportunidade);
    }

    public List<Oportunidade> listarTodas() {
        return oportunidadeRepository.findAll();
    }

    public Optional<Oportunidade> buscarPorId(UUID id) {
        return oportunidadeRepository.findById(id);
    }

    public List<Oportunidade> buscarPorIdLoja(UUID idLoja) {
        return oportunidadeRepository.findByIdLoja(idLoja);
    }

    public Oportunidade atualizar(UUID id, Oportunidade atualizada) {
        return oportunidadeRepository.findById(id).map(o -> {
            if (atualizada.getStatus() == StatusOportunidade.CONCLUIDO &&
                    (atualizada.getMotivoConclusao() == null || atualizada.getMotivoConclusao().isBlank())) {
                throw new IllegalArgumentException("Motivo de conclusão é obrigatório quando status é CONCLUIDO");
            }
            o.setIdLoja(atualizada.getIdLoja());
            o.setStatus(atualizada.getStatus());
            o.setMotivoConclusao(atualizada.getMotivoConclusao());
            o.setCliente(atualizada.getCliente());
            o.setVeiculo(atualizada.getVeiculo());
            return oportunidadeRepository.save(o);
        }).orElseThrow(() -> new RuntimeException("Oportunidade não encontrada"));
    }

    public void deletar(UUID id) {
        oportunidadeRepository.deleteById(id);
    }
}
