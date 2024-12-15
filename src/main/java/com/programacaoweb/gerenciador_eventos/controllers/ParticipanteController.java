package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Participante;
import com.programacaoweb.gerenciador_eventos.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Participante participante = participanteRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nParticipante não encontrado com id: " + id));
        return ResponseEntity.ok().body(participante);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Participante> getParticipanteByNome(@PathVariable String nome) {
        return participanteRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Participante createParticipante(@RequestBody Participante participante) {
        return participanteRepository.save(participante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participante> updateParticipante(@PathVariable Integer id, @RequestBody Participante detalhesParticipante) {
        Participante participante = participanteRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nParticipante não encontrado com id: " + id));

        participante.setNome(detalhesParticipante.getNome());
        participante.setEmail(detalhesParticipante.getEmail());
        participante.setTelefone(detalhesParticipante.getTelefone());

        Participante participanteUpdated = participanteRepository.save(participante);
        return ResponseEntity.ok().body(participanteUpdated);
    }

    @DeleteMapping("/nome/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipanteById(@PathVariable Integer id) {
        participanteRepository.deleteById(id);
    }
}