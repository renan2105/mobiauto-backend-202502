package com.renan.service;

import com.renan.domain.Oportunidade;
import com.renan.domain.Usuario;
import com.renan.domain.enums.StatusOportunidade;
import com.renan.repository.OportunidadeRepository;
import com.renan.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final UsuarioRepository usuarioRepository;

    private static final Set<String> ROLES_GESTORES =
            Set.of("PROPRIETARIO", "ADMINISTRADOR", "GERENTE");

    public OportunidadeService(OportunidadeRepository oportunidadeRepository,
                               UsuarioRepository usuarioRepository) {
        this.oportunidadeRepository = oportunidadeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Oportunidade criar(Oportunidade oportunidade, UUID usuarioId) {

        oportunidade.setId(UUID.randomUUID());

        if (usuarioId != null) {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            validarUsuarioNaLoja(usuario, oportunidade.getIdLoja());
            oportunidade.setUsuarioAtribuido(usuario);
        }

        return oportunidadeRepository.save(oportunidade);
    }

    public Oportunidade atualizar(UUID id, Oportunidade dados, UUID usuarioId) {
        Oportunidade existente = oportunidadeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Oportunidade não encontrada"));

        Usuario usuarioLogado = getUsuarioLogado();

        if (!isGestor(usuarioLogado) &&
                (existente.getUsuarioAtribuido() == null ||
                        !existente.getUsuarioAtribuido().getId().equals(usuarioLogado.getId()))) {
            throw new SecurityException("Você não tem permissão para atualizar esta oportunidade");
        }

        existente.setStatus(dados.getStatus());
        existente.setMotivoConclusao(dados.getMotivoConclusao());
        existente.setCliente(dados.getCliente());
        existente.setVeiculo(dados.getVeiculo());

        if (usuarioId != null) {
            if (!isGestor(usuarioLogado)) {
                throw new SecurityException("Você não tem permissão para mudar o usuário atribuído");
            }

            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            validarUsuarioNaLoja(usuario, existente.getIdLoja());
            existente.setUsuarioAtribuido(usuario);
        }

        if (dados.getStatus() == StatusOportunidade.CONCLUIDO) {
            if (dados.getMotivoConclusao() == null || dados.getMotivoConclusao().isBlank()) {
                throw new IllegalArgumentException("Motivo da conclusão é obrigatório quando status é CONCLUIDO");
            }
            existente.setDataConclusao(new Date());
        }

        return oportunidadeRepository.save(existente);
    }

    public List<Oportunidade> buscarPorIdLoja(UUID idLoja) {
        return oportunidadeRepository.findByIdLoja(idLoja);
    }

    private void validarUsuarioNaLoja(Usuario usuario, UUID idLoja) {
        boolean valido = usuario.getPerfis().stream()
                .anyMatch(p -> p.getLojaId().equals(idLoja));

        if (!valido) {
            throw new IllegalArgumentException("Usuário não possui perfil para esta loja");
        }
    }

    public Optional<Oportunidade> buscarPorId(UUID id) {
        return oportunidadeRepository.findById(id);
    }

    public void deletar(UUID id) {
        oportunidadeRepository.deleteById(id);
    }


    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean isGestor(Usuario usuario) {
        return ROLES_GESTORES.contains(usuario.getCargo().name());
    }
}