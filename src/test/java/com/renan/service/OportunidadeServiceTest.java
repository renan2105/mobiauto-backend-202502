package com.renan.service;

import com.renan.domain.Oportunidade;
import com.renan.domain.embedded.Cliente;
import com.renan.domain.embedded.Veiculo;
import com.renan.domain.enums.StatusOportunidade;
import com.renan.repository.OportunidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OportunidadeServiceTest {

    @Mock
    private OportunidadeRepository oportunidadeRepository;

    @InjectMocks
    private OportunidadeService oportunidadeService;

    private Oportunidade oportunidadeBase;

    @BeforeEach
    void setup() {
        Cliente cliente = new Cliente("João", "joao@email.com", "11999999999");
        Veiculo veiculo = new Veiculo("Fiat", "Argo", "1.3 Drive", 2022);

        oportunidadeBase = new Oportunidade();
        oportunidadeBase.setId(UUID.randomUUID());
        oportunidadeBase.setIdLoja(UUID.randomUUID());
        oportunidadeBase.setStatus(StatusOportunidade.NOVO);
        oportunidadeBase.setCliente(cliente);
        oportunidadeBase.setVeiculo(veiculo);
    }

    @Test
    void deveCriarOportunidadeComStatusNovoPorPadrao() {
        when(oportunidadeRepository.save(any(Oportunidade.class))).thenAnswer(i -> i.getArgument(0));

        Oportunidade criada = oportunidadeService.criar(oportunidadeBase);

        assertNotNull(criada);
        assertEquals(StatusOportunidade.NOVO, criada.getStatus());
        verify(oportunidadeRepository, times(1)).save(any(Oportunidade.class));
    }

    @Test
    void naoDevePermitirAtualizarParaConcluidoSemMotivoConclusao() {
        UUID id = oportunidadeBase.getId();

        when(oportunidadeRepository.findById(id)).thenReturn(Optional.of(oportunidadeBase));

        Oportunidade atualizada = new Oportunidade();
        atualizada.setIdLoja(oportunidadeBase.getIdLoja());
        atualizada.setStatus(StatusOportunidade.CONCLUIDO);
        atualizada.setMotivoConclusao(null);
        atualizada.setCliente(oportunidadeBase.getCliente());
        atualizada.setVeiculo(oportunidadeBase.getVeiculo());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            oportunidadeService.atualizar(id, atualizada);
        });

        assertEquals("Motivo de conclusão é obrigatório quando status é CONCLUIDO", ex.getMessage());

        verify(oportunidadeRepository, never()).save(any());
    }

    @Test
    void deveAtualizarOportunidade() {
        UUID id = oportunidadeBase.getId();
        when(oportunidadeRepository.findById(id)).thenReturn(Optional.of(oportunidadeBase));
        when(oportunidadeRepository.save(any(Oportunidade.class))).thenAnswer(i -> i.getArgument(0));

        Oportunidade atualizada = new Oportunidade();
        atualizada.setIdLoja(oportunidadeBase.getIdLoja());
        atualizada.setStatus(StatusOportunidade.EM_ATENDIMENTO);
        atualizada.setCliente(oportunidadeBase.getCliente());
        atualizada.setVeiculo(oportunidadeBase.getVeiculo());

        Oportunidade resultado = oportunidadeService.atualizar(id, atualizada);

        assertNotNull(resultado);
        assertEquals(StatusOportunidade.EM_ATENDIMENTO, resultado.getStatus());
        verify(oportunidadeRepository, times(1)).findById(id);
        verify(oportunidadeRepository, times(1)).save(any(Oportunidade.class));
    }

    @Test
    void deveBuscarPorId() {
        UUID id = oportunidadeBase.getId();
        when(oportunidadeRepository.findById(id)).thenReturn(Optional.of(oportunidadeBase));

        Optional<Oportunidade> resultado = oportunidadeService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(oportunidadeBase.getId(), resultado.get().getId());
        verify(oportunidadeRepository, times(1)).findById(id);
    }

    @Test
    void deveBuscarPorIdLoja() {
        UUID idLoja = oportunidadeBase.getIdLoja();
        when(oportunidadeRepository.findByIdLoja(idLoja)).thenReturn(List.of(oportunidadeBase));

        List<Oportunidade> resultado = oportunidadeService.buscarPorIdLoja(idLoja);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(idLoja, resultado.get(0).getIdLoja());
        verify(oportunidadeRepository, times(1)).findByIdLoja(idLoja);
    }
}
