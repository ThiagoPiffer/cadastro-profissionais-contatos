package com.Simples.Dental.cadastro_profissionais_contatos.service;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces.IProfissionalRepository;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom.IProfissionalRepositoryCustom;
import com.Simples.Dental.cadastro_profissionais_contatos.service.iservice.IProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService implements IProfissionalService {

    @Autowired
    private IProfissionalRepository profissionalRepository;

    @Autowired
    private IProfissionalRepositoryCustom profissionalRepositoryCustom;

    @Override
    public Optional<List<Object[]>> listarProfissionais(String q, List<String> fields) {
        return profissionalRepositoryCustom.consultarPorNomeOuCargo(q, fields);
    }


    @Override
    public Optional<Profissional> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }

    @Override
    public Profissional criar(Profissional profissional) {
        profissional.setCreatedDate(LocalDate.now());
        return profissionalRepository.save(profissional);
    }

    @Override
    public Optional<Profissional> atualizar(Long id, Profissional profissionalAtualizado) {
        return profissionalRepository.findById(id)
                .map(profissional -> {
                    profissional.setNome(profissionalAtualizado.getNome());
                    profissional.setCargo(profissionalAtualizado.getCargo());
                    profissional.setNascimento(profissionalAtualizado.getNascimento());
                    return profissionalRepository.save(profissional);
                });
    }

    @Override
    public void deletar(Long id) {
        profissionalRepository.findById(id).ifPresent(profissional -> {
            profissional.setAtivo(false);
            profissionalRepository.save(profissional);
        });
    }
}
