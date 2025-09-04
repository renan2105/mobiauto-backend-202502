package com.renan.controller;

import com.renan.dto.LojaRequestDTO;
import com.renan.dto.LojaResponseDTO;
import com.renan.service.LojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lojas")
public class LojaController {

    private final LojaService service;

    public LojaController(LojaService service) {
        this.service = service;
    }

    @GetMapping
    public List<LojaResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public LojaResponseDTO buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<LojaResponseDTO> criar(@RequestBody LojaRequestDTO dto) {
        LojaResponseDTO response = service.criar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public LojaResponseDTO atualizar(@PathVariable UUID id, @RequestBody LojaRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
