package com.renan.service;

import com.renan.domain.Loja;
import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.LojaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LojaService {

    private final LojaRepository repository;

    public LojaService(LojaRepository repository) {
        this.repository = repository;
    }

    public List<LojaResponseDTO> listar() {
        return repository.findAll().stream()
                .map(loja -> new LojaResponseDTO(loja.getId(), loja.getNome()))
                .collect(Collectors.toList());
    }

    public LojaResponseDTO buscarPorId(UUID id) {
        Loja loja = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada: " + id));
        return new LojaResponseDTO(loja.getId(), loja.getNome());
    }

    public LojaResponseDTO criar(LojaRequestDTO dto) {
        Loja loja = new Loja();
        loja.setNome(dto.getNome());
        loja = repository.save(loja);
        return new LojaResponseDTO(loja.getId(), loja.getNome());
    }

    public LojaResponseDTO atualizar(UUID id, LojaRequestDTO dto) {
        Loja loja = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada: " + id));
        loja.setNome(dto.getNome());
        loja = repository.save(loja);
        return new LojaResponseDTO(loja.getId(), loja.getNome());
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Loja não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
