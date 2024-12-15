package com.programacaoweb.gerenciador_eventos.repositories;

import com.programacaoweb.gerenciador_eventos.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findByNomeContainingIgnoreCase(String nome);
}