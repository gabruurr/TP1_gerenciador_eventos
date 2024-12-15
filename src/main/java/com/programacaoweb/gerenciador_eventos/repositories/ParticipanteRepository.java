package com.programacaoweb.gerenciador_eventos.repositories;

import com.programacaoweb.gerenciador_eventos.entities.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    List<Participante> findByNomeContainingIgnoreCase(String nome);

}