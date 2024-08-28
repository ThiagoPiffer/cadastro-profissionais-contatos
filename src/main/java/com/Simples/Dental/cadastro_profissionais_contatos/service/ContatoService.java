package com.Simples.Dental.cadastro_profissionais_contatos.service;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IContatoRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom.IContatoRepositoryCustom;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IProfissionalRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.service.iservice.IContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService implements IContatoService {

    @Autowired
    private IContatoRepository contatoRepository;

    @Autowired
    private IContatoRepositoryCustom contatoRepositoryCustom;

    @Autowired
    private IProfissionalRepository profissionalRepository;

    @Override
    public Optional<List<Object[]>> listarContatos(String q, List<String> fields) {
        return Optional.of(contatoRepositoryCustom.consultarPorNomeOuContato(q, fields).orElseGet(List::of));
    }

    @Override
    public Optional<Contato> buscarPorId(Long id) {
        return contatoRepository.findById(id);
    }

    @Override
    public Contato criar(Contato contato) {
        if (contato.getProfissional() != null && contato.getProfissional().getId() != null) {
            Profissional profissional = profissionalRepository.findById(contato.getProfissional().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado com ID: " + contato.getProfissional().getId()));
            contato.setProfissional(profissional);
        } else {
            throw new IllegalArgumentException("Profissional não pode ser nulo ao criar um contato.");
        }

        return contatoRepository.save(contato);
    }

    @Override
    public Optional<Contato> atualizar(Long id, Contato contatoAtualizado) {
        return contatoRepository.findById(id)
                .map(contato -> {
                    contato.setNome(contatoAtualizado.getNome());
                    contato.setContato(contatoAtualizado.getContato());
                    contato.setProfissional(contatoAtualizado.getProfissional());
                    return contatoRepository.save(contato);
                });
    }

    @Override
    public void deletar(Long id) {
        contatoRepository.findById(id).ifPresent(contato -> {
            contato.setAtivo(false);
            contatoRepository.save(contato);
        });
    }
}
