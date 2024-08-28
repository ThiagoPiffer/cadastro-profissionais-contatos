package com.Simples.Dental.cadastro_profissionais_contatos.service;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IProfissionalRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom.IProfissionalRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfissionalServiceTest {

    @Mock
    private IProfissionalRepository profissionalRepository;

    @Mock
    private IProfissionalRepositoryCustom profissionalRepositoryCustom;

    @InjectMocks
    private ProfissionalService profissionalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Carlos de Souza");
        profissional.setCargo("Dentista");

        when(profissionalRepository.save(profissional)).thenReturn(profissional);

        Profissional result = profissionalService.criar(profissional);

        assertEquals("Carlos de Souza", result.getNome());
        assertEquals("Dentista", result.getCargo());
        verify(profissionalRepository, times(1)).save(profissional);
    }

    @Test
    void testBuscarProfissionalPorId() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Carlos de Souza");

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));

        Optional<Profissional> result = profissionalService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Carlos de Souza", result.get().getNome());
        verify(profissionalRepository, times(1)).findById(1L);
    }

    @Test
    void testAtualizarProfissional() {
        Profissional profissionalExistente = new Profissional();
        profissionalExistente.setId(1L);
        profissionalExistente.setNome("Ana de Lima");
        profissionalExistente.setCargo("Auxiliar");

        Profissional profissionalAtualizado = new Profissional();
        profissionalAtualizado.setId(1L);
        profissionalAtualizado.setNome("Ana Souza");
        profissionalAtualizado.setCargo("Gerente");

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissionalExistente));
        when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissionalAtualizado);

        Optional<Profissional> result = profissionalService.atualizar(1L, profissionalAtualizado);

        assertTrue(result.isPresent());
        assertEquals("Ana Souza", result.get().getNome());
        assertEquals("Gerente", result.get().getCargo());
        verify(profissionalRepository, times(1)).findById(1L);
        verify(profissionalRepository, times(1)).save(profissionalExistente);
    }

    @Test
    void testDeletarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setAtivo(true);

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));

        profissionalService.deletar(1L);

        verify(profissionalRepository, times(1)).findById(1L);
        verify(profissionalRepository, times(1)).save(profissional);
        assertFalse(profissional.isAtivo());
    }

    @Test
    void testListarTodosProfissionaisAtivos() {
        Object[] profissional1 = {"Carlos de Souza", true};
        Object[] profissional2 = {"Maria Oliveira", true};

        List<Object[]> profissionais = Arrays.asList(profissional1, profissional2);

        when(profissionalRepositoryCustom.consultarPorNomeOuCargo("", new ArrayList<>())).thenReturn(Optional.of(profissionais));

        Optional<List<Object[]>> result = profissionalService.listarProfissionais("", new ArrayList<>());

        assertEquals(2, result.get().size());
        verify(profissionalRepositoryCustom, times(1)).consultarPorNomeOuCargo("", new ArrayList<>());
    }
}
