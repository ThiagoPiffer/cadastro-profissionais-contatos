package com.Simples.Dental.cadastro_profissionais_contatos.repository;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IContatoRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IContatoRepositoryTest {

    @Autowired
    private IContatoRepository contatoRepository;

    @Autowired
    private IProfissionalRepository profissionalRepository;

    @Test
    void testFindById() {
        Profissional profissional = new Profissional();
        profissional.setNome("Ana Maria");
        profissional.setCargo("Dentista");
        profissional.setNascimento(LocalDate.of(1990, 5, 15));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        Contato contato = new Contato();
        contato.setNome("João da Silva");
        contato.setContato("joao.silva@example.com");
        contato.setCreatedDate(LocalDateTime.now());
        contato.setAtivo(true);
        contato.setProfissional(profissional);

        contatoRepository.save(contato);

        Optional<Contato> found = contatoRepository.findById(contato.getId());
        assertTrue(found.isPresent());
        assertEquals("João da Silva", found.get().getNome());
    }

    @Test
    void testSalvarContato() {
        Profissional profissional = new Profissional();
        profissional.setNome("Carlos Eduardo");
        profissional.setCargo("Ortopedista");
        profissional.setNascimento(LocalDate.of(1985, 3, 20));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        Contato contato = new Contato();
        contato.setNome("Maria Santos");
        contato.setContato("maria.santos@example.com");
        contato.setCreatedDate(LocalDateTime.now());
        contato.setAtivo(true);
        contato.setProfissional(profissional);

        Contato savedContato = contatoRepository.save(contato);

        assertNotNull(savedContato.getId());
        assertEquals("Maria Santos", savedContato.getNome());
    }

    @Test
    void testAtualizarContato() {
        Profissional profissional = new Profissional();
        profissional.setNome("Fernanda Lima");
        profissional.setCargo("Higienista");
        profissional.setNascimento(LocalDate.of(1988, 7, 10));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        Contato contato = new Contato();
        contato.setNome("Pedro Rocha");
        contato.setContato("pedro.rocha@example.com");
        contato.setCreatedDate(LocalDateTime.now());
        contato.setAtivo(true);
        contato.setProfissional(profissional);

        contatoRepository.save(contato);

        contato.setNome("Pedro Rocha Filho");
        contato.setContato("pedro.rochafilho@example.com");
        Contato updatedContato = contatoRepository.save(contato);

        assertEquals("Pedro Rocha Filho", updatedContato.getNome());
        assertEquals("pedro.rochafilho@example.com", updatedContato.getContato());
    }

    @Test
    void testExcluirContato() {
        Profissional profissional = new Profissional();
        profissional.setNome("Gustavo Neves");
        profissional.setCargo("Periodontista");
        profissional.setNascimento(LocalDate.of(1975, 11, 25));
        profissional.setCreatedDate(LocalDate.now());
        profissional.setAtivo(true);

        profissionalRepository.save(profissional);

        Contato contato = new Contato();
        contato.setNome("Joana Silva");
        contato.setContato("joana.silva@example.com");
        contato.setCreatedDate(LocalDateTime.now());
        contato.setAtivo(true);
        contato.setProfissional(profissional);

        contatoRepository.save(contato);

        contatoRepository.delete(contato);

        Optional<Contato> found = contatoRepository.findById(contato.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAllAtivos() {
        Profissional profissional1 = new Profissional();
        profissional1.setNome("Ricardo Pereira");
        profissional1.setCargo("Endodontista");
        profissional1.setNascimento(LocalDate.of(1980, 8, 30));
        profissional1.setCreatedDate(LocalDate.now());
        profissional1.setAtivo(true);
        profissionalRepository.save(profissional1);

        Contato contato1 = new Contato();
        contato1.setNome("Ricardo Neto");
        contato1.setContato("ricardo.neto@example.com");
        contato1.setCreatedDate(LocalDateTime.now());
        contato1.setAtivo(true);
        contato1.setProfissional(profissional1);
        contatoRepository.save(contato1);

        Profissional profissional2 = new Profissional();
        profissional2.setNome("Lucas Santana");
        profissional2.setCargo("Cirurgião");
        profissional2.setNascimento(LocalDate.of(1995, 12, 18));
        profissional2.setCreatedDate(LocalDate.now());
        profissional2.setAtivo(true);
        profissionalRepository.save(profissional2);

        Contato contato2 = new Contato();
        contato2.setNome("Lucas Júnior");
        contato2.setContato("lucas.junior@example.com");
        contato2.setCreatedDate(LocalDateTime.now());
        contato2.setAtivo(true);
        contato2.setProfissional(profissional2);
        contatoRepository.save(contato2);

        List<Contato> contatosAtivos = contatoRepository.findAll();

        assertEquals(2, contatosAtivos.size());
        assertTrue(contatosAtivos.stream().allMatch(Contato::isAtivo));
    }
}
