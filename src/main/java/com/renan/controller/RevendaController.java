package com.renan.controller;

import com.renan.dto.RevendaRequestDTO;
import com.renan.dto.RevendaResponseDTO;
import com.renan.service.RevendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/revendas")
public class RevendaController {

    private final RevendaService service;

    public RevendaController(RevendaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RevendaResponseDTO> criar(@Valid @RequestBody RevendaRequestDTO dto) {
        RevendaResponseDTO response = service.criar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RevendaResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public RevendaResponseDTO buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public RevendaResponseDTO atualizar(@PathVariable UUID id,@Valid @RequestBody RevendaRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
