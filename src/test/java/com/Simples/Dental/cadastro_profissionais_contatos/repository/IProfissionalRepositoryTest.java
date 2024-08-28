package com.Simples.Dental.cadastro_profissionais_contatos.repository;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IProfissionalRepositoryTest {

    @Autowired
    private IProfissionalRepository profissionalRepository;

    @Test
    void testFindById() {
        Profissional profissional = new Profissional();
        profissional.setNome("João da Silva");
        profissional.setCargo("Ortopedista");
        profissional.setNascimento(LocalDate.of(1985, 4, 10));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        Optional<Profissional> found = profissionalRepository.findById(profissional.getId());
        assertTrue(found.isPresent());
        assertEquals("João da Silva", found.get().getNome());
    }

    @Test
    void testSalvarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Maria Oliveira");
        profissional.setCargo("Dentista");
        profissional.setNascimento(LocalDate.of(1990, 8, 20));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        Profissional savedProfissional = profissionalRepository.save(profissional);

        assertNotNull(savedProfissional.getId());
        assertEquals("Maria Oliveira", savedProfissional.getNome());
    }

    @Test
    void testAtualizarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Carlos Pereira");
        profissional.setCargo("Endodontista");
        profissional.setNascimento(LocalDate.of(1978, 6, 15));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        profissional.setNome("Carlos Eduardo Pereira");
        profissional.setCargo("Periodontista");
        Profissional updatedProfissional = profissionalRepository.save(profissional);

        assertEquals("Carlos Eduardo Pereira", updatedProfissional.getNome());
        assertEquals("Periodontista", updatedProfissional.getCargo());
    }

    @Test
    void testExcluirProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Fernanda Lima");
        profissional.setCargo("Higienista");
        profissional.setNascimento(LocalDate.of(1992, 3, 5));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        profissionalRepository.delete(profissional);

        Optional<Profissional> found = profissionalRepository.findById(profissional.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAllAtivos() {
        Profissional profissional1 = new Profissional();
        profissional1.setNome("Rafael Costa");
        profissional1.setCargo("Cirurgião");
        profissional1.setNascimento(LocalDate.of(1980, 11, 30));
        profissional1.setCreatedDate(LocalDate.now());
        profissional1.setAtivo(true);
        profissionalRepository.save(profissional1);

        Profissional profissional2 = new Profissional();
        profissional2.setNome("Luiza Martins");
        profissional2.setCargo("Pediatra");
        profissional2.setNascimento(LocalDate.of(1989, 1, 15));
        profissional2.setCreatedDate(LocalDate.now());
        profissional2.setAtivo(true);
        profissionalRepository.save(profissional2);

        List<Profissional> profissionaisAtivos = profissionalRepository.findAll();

        assertEquals(2, profissionaisAtivos.size());
        assertTrue(profissionaisAtivos.stream().allMatch(Profissional::isAtivo));
    }
}
