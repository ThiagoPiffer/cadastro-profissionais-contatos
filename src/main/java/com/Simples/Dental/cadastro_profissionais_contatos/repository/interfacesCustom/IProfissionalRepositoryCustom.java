package com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom;

import java.util.List;
import java.util.Optional;

public interface IProfissionalRepositoryCustom {
    Optional<List<Object[]>>  consultarPorNomeOuCargo(String q, List<String> fields);
}
