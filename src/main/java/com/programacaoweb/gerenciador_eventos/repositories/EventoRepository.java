package com.programacaoweb.gerenciador_eventos.repositories;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByNomeContainingIgnoreCase(String nome);
}

