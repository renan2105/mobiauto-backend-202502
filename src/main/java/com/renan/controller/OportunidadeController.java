package com.renan.controller;

import com.renan.domain.Oportunidade;
import com.renan.domain.embedded.Cliente;
import com.renan.domain.embedded.Veiculo;
import com.renan.dto.*;
import com.renan.service.OportunidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/oportunidades")
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;

    public OportunidadeController(OportunidadeService oportunidadeService) {
        this.oportunidadeService = oportunidadeService;
    }

    @PostMapping
    public ResponseEntity<OportunidadeResponseDTO> criar(@Valid @RequestBody OportunidadeRequestDTO request) {
        Oportunidade oportunidade = mapperDtoToEntity(request);
        Oportunidade salvo = oportunidadeService.criar(oportunidade);
        return ResponseEntity.ok(mapperEntityToResponse(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OportunidadeResponseDTO> atualizar(@PathVariable UUID id,
                                                             @Valid @RequestBody OportunidadeRequestDTO request) {
        Oportunidade oportunidade = mapperDtoToEntity(request);
        Oportunidade atualizado = oportunidadeService.atualizar(id, oportunidade);
        return ResponseEntity.ok(mapperEntityToResponse(atualizado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OportunidadeResponseDTO> buscarPorId(@PathVariable UUID id) {
        return oportunidadeService.buscarPorId(id)
                .map(o -> ResponseEntity.ok(mapperEntityToResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/loja/{idLoja}")
    public ResponseEntity<List<OportunidadeResponseDTO>> buscarPorIdLoja(@PathVariable UUID idLoja) {
        List<OportunidadeResponseDTO> lista = oportunidadeService.buscarPorIdLoja(idLoja)
                .stream()
                .map(this::mapperEntityToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        oportunidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Oportunidade mapperDtoToEntity(OportunidadeRequestDTO dto) {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setIdLoja(dto.getIdLoja());
        oportunidade.setStatus(dto.getStatus());
        oportunidade.setMotivoConclusao(dto.getMotivoConclusao());

        Cliente cliente = new Cliente(dto.getCliente().getNome(),
                dto.getCliente().getEmail(),
                dto.getCliente().getTelefone());
        oportunidade.setCliente(cliente);

        Veiculo veiculo = new Veiculo(dto.getVeiculo().getMarca(),
                dto.getVeiculo().getModelo(),
                dto.getVeiculo().getVersao(),
                dto.getVeiculo().getAno());
        oportunidade.setVeiculo(veiculo);

        return oportunidade;
    }

    private OportunidadeResponseDTO mapperEntityToResponse(Oportunidade o) {
        return new OportunidadeResponseDTO(
                o.getId(),
                o.getIdLoja(),
                o.getStatus(),
                o.getMotivoConclusao(),
                new ClienteResponseDTO(o.getCliente().getNome(), o.getCliente().getEmail(), o.getCliente().getTelefone()),
                new VeiculoResponseDTO(o.getVeiculo().getMarca(), o.getVeiculo().getModelo(), o.getVeiculo().getVersao(), o.getVeiculo().getAno())
        );
    }


}
