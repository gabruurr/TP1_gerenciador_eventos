package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Participante;
import com.programacaoweb.gerenciador_eventos.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @GetMapping
    public ResponseEntity<List<Participante>> findAllParticipante() {
        List<Participante> participantes = participanteRepository.findAll();
        return ResponseEntity.ok().body(participantes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Participante> getParticipanteById(@PathVariable Integer id) {
        Participante participante = participanteRepository.findById(id).get();
        return ResponseEntity.ok().body(participante);
    }

    @GetMapping(value = "/{nome}")
    public List<Participante> getParticipanteByNome(@PathVariable String nome) {
        return participanteRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    public Participante createParticipante(@RequestBody Participante participante) {
        return participanteRepository.save(participante);
    }
    @DeleteMapping("/nome/{id}")
    public ResponseEntity<Void> deleteParticipanteById(@PathVariable Integer id) {
        participanteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}