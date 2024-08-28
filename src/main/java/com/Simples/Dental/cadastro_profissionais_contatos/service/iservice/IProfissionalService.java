package com.Simples.Dental.cadastro_profissionais_contatos.service.iservice;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;

import java.util.List;
import java.util.Optional;

public interface IProfissionalService {

    //List<Profissional> listarTodos(String q);
    Optional<Profissional> buscarPorId(Long id);
    Profissional criar(Profissional profissional);
    Optional<Profissional> atualizar(Long id, Profissional profissionalAtualizado);
    void deletar(Long id);

    Optional<List<Object[]>>  listarProfissionais(String q, List<String> fields);
}
