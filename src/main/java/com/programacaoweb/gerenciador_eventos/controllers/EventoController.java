package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping
    public ResponseEntity<List<Evento>> findAllEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return ResponseEntity.ok().body(eventos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Integer id) {
        Evento evento = eventoRepository.findById(id).get();
        return ResponseEntity.ok().body(evento);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Evento> getEventoByNome(@PathVariable String nome) {
        return eventoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventoById(@PathVariable Integer id) {
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
