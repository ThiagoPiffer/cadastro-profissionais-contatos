package com.Simples.Dental.cadastro_profissionais_contatos.repository.interfaces;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContatoRepository extends JpaRepository<Contato, Long> {

}
