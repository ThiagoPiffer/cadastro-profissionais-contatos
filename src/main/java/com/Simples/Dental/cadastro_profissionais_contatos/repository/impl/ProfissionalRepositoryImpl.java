package com.Simples.Dental.cadastro_profissionais_contatos.repository.impl;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom.IProfissionalRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfissionalRepositoryImpl implements IProfissionalRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Object[]>> consultarPorNomeOuCargo(String termoBusca, List<String> campos) {
        StringBuilder jpql = new StringBuilder("SELECT ");

        if (campos != null && !campos.isEmpty()) {
            String selecaoCampos = String.join(", p.", campos);
            jpql.append("p.").append(selecaoCampos);
        } else {
            jpql.append("p");
        }

        jpql.append(" FROM Profissional p WHERE p.ativo = true");

        if (termoBusca != null && !termoBusca.isEmpty()) {
            jpql.append(" AND (LOWER(p.nome) LIKE LOWER(CONCAT('%', :termoBusca, '%')) OR LOWER(p.cargo) LIKE LOWER(CONCAT('%', :termoBusca, '%')))");
        }

        TypedQuery<Object[]> consulta = entityManager.createQuery(jpql.toString(), Object[].class);

        if (termoBusca != null && !termoBusca.isEmpty()) {
            consulta.setParameter("termoBusca", termoBusca);
        }

        List<Object[]> resultado = consulta.getResultList();
        return Optional.ofNullable(resultado.isEmpty() ? null : resultado);
    }

}
