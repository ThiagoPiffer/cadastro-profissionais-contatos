package com.Simples.Dental.cadastro_profissionais_contatos.service.iservice;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;

import java.util.List;
import java.util.Optional;

public interface IContatoService {

    //List<Contato> listarTodos(String q);
    Optional<Contato> buscarPorId(Long id);
    Contato criar(Contato contato);
    Optional<Contato> atualizar(Long id, Contato contatoAtualizado);
    void deletar(Long id);

    Optional<List<Object[]>>  listarContatos(String q, List<String> fields);

}
