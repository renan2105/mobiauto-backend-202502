package com.renan.service;

import com.renan.domain.Oportunidade;
import com.renan.domain.PerfilLoja;
import com.renan.domain.Usuario;
import com.renan.domain.embedded.Cliente;
import com.renan.domain.embedded.Veiculo;
import com.renan.domain.enums.Cargo;
import com.renan.domain.enums.StatusOportunidade;
import com.renan.dto.ClienteRequestDTO;
import com.renan.dto.OportunidadeRequestDTO;
import com.renan.dto.VeiculoRequestDTO;
import com.renan.repository.OportunidadeRepository;
import com.renan.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OportunidadeServiceTest {

    @Mock
    private OportunidadeRepository repo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMocks
    private OportunidadeService service;

    private Usuario usuarioLogado;
    private Oportunidade oportunidade;
    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setup() {

        oportunidade = new Oportunidade();
        oportunidade.setId(UUID.randomUUID());
        oportunidade.setIdLoja(UUID.randomUUID());
        oportunidade.setStatus(StatusOportunidade.NOVO);
        oportunidade.setCliente(new Cliente("Cliente", "cli@email.com", "1199999"));
        oportunidade.setVeiculo(new Veiculo("VW", "Gol", "1.0", 2020));

        usuarioLogado = new Usuario();
        usuarioLogado.setId(UUID.randomUUID());
        usuarioLogado.setNome("Usuário Logado");
        usuarioLogado.setCargo(Cargo.ADMINISTRADOR);
        usuarioLogado.setPerfis(new ArrayList<>());
        usuarioLogado.getPerfis().add(new PerfilLoja(oportunidade.getIdLoja(), Cargo.ADMINISTRADOR));

        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        when(authentication.getPrincipal()).thenReturn(usuarioLogado);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void atualizarDeveNegarSeUsuarioNaoAtribuidoENaoGestor() {
        usuarioLogado.setCargo(Cargo.ASSISTENTE);
        oportunidade.setUsuarioAtribuido(usuarioLogado);

        when(repo.findById(oportunidade.getId())).thenReturn(Optional.of(oportunidade));

        OportunidadeRequestDTO dto = new OportunidadeRequestDTO();
        dto.setCliente(new ClienteRequestDTO("João", "joao@mail.com", "1111"));
        dto.setVeiculo(new VeiculoRequestDTO("VW", "Fox", "1.6", 2019));
        dto.setStatus(StatusOportunidade.EM_ATENDIMENTO);

        assertThrows(SecurityException.class,
                () -> service.atualizar(oportunidade.getId(), oportunidade, oportunidade.getUsuarioAtribuido().getId()));
    }

    @Test
    void atualizarDeveLancarExcecaoSeConclusaoSemMotivo() {
        oportunidade.setUsuarioAtribuido(usuarioLogado);
        when(repo.findById(oportunidade.getId())).thenReturn(Optional.of(oportunidade));

        OportunidadeRequestDTO dto = new OportunidadeRequestDTO();
        dto.setCliente(new ClienteRequestDTO("João", "joao@mail.com", "1111"));
        dto.setVeiculo(new VeiculoRequestDTO("VW", "Fox", "1.6", 2019));
        dto.setStatus(StatusOportunidade.CONCLUIDO);

        assertThrows(IllegalArgumentException.class,
                () -> service.atualizar(oportunidade.getId(), oportunidade, oportunidade.getUsuarioAtribuido().getId()));
    }

    @Test
    void atualizarDeveConcluirComMotivo() {
        oportunidade.setUsuarioAtribuido(usuarioLogado);
        oportunidade.setStatus(StatusOportunidade.CONCLUIDO);
        oportunidade.setMotivoConclusao("Venda concluída");


        when(repo.findById(oportunidade.getId())).thenReturn(Optional.of(oportunidade));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(usuarioRepo.findById(any())).thenReturn(Optional.ofNullable(usuarioLogado));

        OportunidadeRequestDTO dto = new OportunidadeRequestDTO();
        dto.setCliente(new ClienteRequestDTO("João", "joao@mail.com", "1111"));
        dto.setVeiculo(new VeiculoRequestDTO("VW", "Fox", "1.6", 2019));
        dto.setStatus(StatusOportunidade.CONCLUIDO);
        dto.setMotivoConclusao("Venda concluída");

        var resp = service.atualizar(oportunidade.getId(), oportunidade, oportunidade.getUsuarioAtribuido().getId());

        assertEquals(StatusOportunidade.CONCLUIDO, resp.getStatus());
        assertNotNull(resp.getDataConclusao());
    }

    @Test
    void gestorPodeTrocarUsuarioAtribuido() {

        usuarioLogado.setCargo(Cargo.ADMINISTRADOR);
        when(repo.findById(oportunidade.getId())).thenReturn(Optional.of(oportunidade));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(UUID.randomUUID());
        var perfil = new com.renan.domain.PerfilLoja();
        perfil.setLojaId(oportunidade.getIdLoja());
        novoUsuario.setPerfis(List.of(perfil));
        oportunidade.setUsuarioAtribuido(novoUsuario);

        when(usuarioRepo.findById(novoUsuario.getId())).thenReturn(Optional.of(novoUsuario));

        OportunidadeRequestDTO dto = new OportunidadeRequestDTO();
        dto.setCliente(new ClienteRequestDTO("João", "joao@mail.com", "1111"));
        dto.setVeiculo(new VeiculoRequestDTO("VW", "Fox", "1.6", 2019));
        dto.setStatus(StatusOportunidade.EM_ATENDIMENTO);
        dto.setUsuarioId(novoUsuario.getId());

        var resp = service.atualizar(oportunidade.getId(), oportunidade, oportunidade.getUsuarioAtribuido().getId());

        assertNotNull(resp.getUsuarioAtribuido());
        assertEquals(novoUsuario.getId(), resp.getUsuarioAtribuido().getId());
        assertNotNull(resp.getDataAtribuicao());
    }

}
