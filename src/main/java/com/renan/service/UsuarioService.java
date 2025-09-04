package com.renan.service;

import com.renan.domain.Usuario;
import com.renan.dto.UsuarioRequestDTO;
import com.renan.dto.UsuarioResponseDTO;
import com.renan.exception.ResourceNotFoundException;
import com.renan.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getCargo()))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCargo());
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setCargo(dto.getCargo());

        Usuario salvo = repository.save(usuario);
        return new UsuarioResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getCargo());
    }

    public UsuarioResponseDTO atualizar(UUID id, UsuarioRequestDTO dto) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setSenha(dto.getSenha());
        existente.setCargo(dto.getCargo());

        Usuario atualizado = repository.save(existente);
        return new UsuarioResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getEmail(), atualizado.getCargo());
    }

    public void deletar(UUID id) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        repository.delete(existente);
    }
}
