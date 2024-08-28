package com.Simples.Dental.cadastro_profissionais_contatos.service;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IContatoRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IProfissionalRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom.IContatoRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ContatoServiceTest {

    @Mock
    private IContatoRepository contatoRepository;

    @Mock
    private IProfissionalRepository profissionalRepository;

    @Mock
    private IContatoRepositoryCustom contatoRepositoryCustom;

    @InjectMocks
    private ContatoService contatoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarContatoPorId() {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("João da Silva");

        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));

        Optional<Contato> result = contatoService.buscarPorId(1L);

        assertEquals("João da Silva", result.get().getNome());
        verify(contatoRepository, times(1)).findById(1L);
    }

    @Test
    void testSalvarContato() {
        Contato contato = new Contato();
        contato.setNome("Maria da Silva");
        contato.setContato("maria.silva@example.com");

        Profissional profissional = new Profissional();
        profissional.setId(1L);
        contato.setProfissional(profissional);

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(contatoRepository.save(contato)).thenReturn(contato);

        Contato result = contatoService.criar(contato);

        assertEquals("Maria da Silva", result.getNome());
        assertEquals("maria.silva@example.com", result.getContato());
        verify(profissionalRepository, times(1)).findById(1L);
        verify(contatoRepository, times(1)).save(contato);
    }


    @Test
    void testAtualizarContato() {
        Contato contatoExistente = new Contato();
        contatoExistente.setId(1L);
        contatoExistente.setNome("Ana da Silva");
        contatoExistente.setContato("ana.silva@example.com");

        Contato contatoAtualizado = new Contato();
        contatoAtualizado.setNome("Ana Souza");
        contatoAtualizado.setContato("ana.souza@example.com");

        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contatoExistente));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contatoExistente);

        Optional<Contato> result = contatoService.atualizar(1L, contatoAtualizado);

        assertEquals("Ana Souza", result.get().getNome());
        assertEquals("ana.souza@example.com", result.get().getContato());
        verify(contatoRepository, times(1)).findById(1L);
        verify(contatoRepository, times(1)).save(contatoExistente);
    }


    @Test
    void testDeletarContato() {
        Contato contato = new Contato();
        contato.setId(2L);
        contato.setNome("Carlos Santos");
        contato.setAtivo(true);

        when(contatoRepository.findById(2L)).thenReturn(Optional.of(contato));

        contatoService.deletar(2L);

        assertFalse(contato.isAtivo());
        verify(contatoRepository, times(1)).findById(2L);
        verify(contatoRepository, times(1)).save(contato);
    }

    @Test
    void testBuscarTodosContatosAtivos() {
        Object[] contato1 = {"João da Silva", true};
        Object[] contato2 = {"Maria Oliveira", true};

        List<Object[]> contatos = Arrays.asList(contato1, contato2);

        when(contatoRepositoryCustom.consultarPorNomeOuContato("", new ArrayList<>())).thenReturn(Optional.of(contatos));

        Optional<List<Object[]>> result = contatoService.listarContatos("", new ArrayList<>());

        assertEquals(2, result.get().size());
        verify(contatoRepositoryCustom, times(1)).consultarPorNomeOuContato("", new ArrayList<>());
    }


    @Test
    void testBuscarContatoPorIdNaoEncontrado() {
        when(contatoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Contato> result = contatoService.buscarPorId(99L);

        assertEquals(Optional.empty(), result);
        verify(contatoRepository, times(1)).findById(99L);
    }

}
