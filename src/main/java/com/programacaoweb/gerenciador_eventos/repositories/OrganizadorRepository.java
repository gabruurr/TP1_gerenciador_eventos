package com.programacaoweb.gerenciador_eventos.repositories;

import com.programacaoweb.gerenciador_eventos.entities.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizadorRepository extends JpaRepository<Organizador, Integer> {
    List<Organizador> findByNomeContainingIgnoreCase(String nome);

}