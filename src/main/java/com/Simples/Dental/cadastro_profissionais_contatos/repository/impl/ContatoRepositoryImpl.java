package com.Simples.Dental.cadastro_profissionais_contatos.repository.impl;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.repository.interfacesCustom.IContatoRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ContatoRepositoryImpl implements IContatoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Object[]>> consultarPorNomeOuContato(String termoBusca, List<String> campos) {
        StringBuilder jpql = new StringBuilder("SELECT ");

        if (campos != null && !campos.isEmpty()) {
            String selecaoCampos = String.join(", c.", campos);
            jpql.append("c.").append(selecaoCampos);
        } else {
            jpql.append("c");
        }

        jpql.append(" FROM Contato c WHERE c.ativo = true");

        if (termoBusca != null && !termoBusca.isEmpty()) {
            jpql.append(" AND (LOWER(c.nome) LIKE LOWER(CONCAT('%', :termoBusca, '%')) OR LOWER(c.contato) LIKE LOWER(CONCAT('%', :termoBusca, '%')))");
        }

        TypedQuery<Object[]> consulta = entityManager.createQuery(jpql.toString(), Object[].class);

        if (termoBusca != null && !termoBusca.isEmpty()) {
            consulta.setParameter("termoBusca", termoBusca);
        }

        List<Object[]> resultado = consulta.getResultList();
        return Optional.ofNullable(resultado.isEmpty() ? null : resultado);
    }

}
