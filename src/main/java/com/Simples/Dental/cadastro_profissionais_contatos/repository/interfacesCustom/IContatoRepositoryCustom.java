package com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom;

import java.util.List;
import java.util.Optional;

public interface IContatoRepositoryCustom {
    Optional<List<Object[]>> consultarPorNomeOuContato(String q, List<String> fields);
}
