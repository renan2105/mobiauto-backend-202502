package com.renan.service;

import com.renan.domain.Revenda;
import com.renan.dto.RevendaRequestDTO;
import com.renan.dto.RevendaResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.RevendaRepository;
import com.renan.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RevendaService {

    private final RevendaRepository repository;

    private final SecurityUtils securityUtils;

    public RevendaService(RevendaRepository repository, SecurityUtils securityUtils) {
        this.repository = repository;
        this.securityUtils = securityUtils;
    }

    public List<RevendaResponseDTO> listar() {
        return repository.findAll().stream()
                .map(revenda -> new RevendaResponseDTO(revenda.getId(), revenda.getNome(), revenda.getCnpj()))
                .collect(Collectors.toList());
    }

    public RevendaResponseDTO buscarPorId(UUID id) {
        Revenda revenda = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenda não encontrada: " + id));
        return new RevendaResponseDTO(revenda.getId(), revenda.getNome(), revenda.getCnpj());
    }

    public RevendaResponseDTO criar(RevendaRequestDTO dto) {
        Revenda revenda = new Revenda();
        revenda.setNome(dto.getNome());
        revenda.setCnpj(dto.getCnpj());
        revenda = repository.save(revenda);
        return new RevendaResponseDTO(revenda.getId(), revenda.getNome(), revenda.getCnpj());
    }

    public RevendaResponseDTO atualizar(UUID id, RevendaRequestDTO dto) {
        securityUtils.checkManagePermission(id);
        Revenda revenda = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenda não encontrada: " + id));
        revenda.setNome(dto.getNome());
        revenda.setCnpj(dto.getCnpj());
        revenda = repository.save(revenda);
        return new RevendaResponseDTO(revenda.getId(), revenda.getNome(), revenda.getCnpj());
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Revenda não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
