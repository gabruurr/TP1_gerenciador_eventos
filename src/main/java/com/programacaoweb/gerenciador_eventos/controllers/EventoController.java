package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Evento evento = eventoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nEvento não encontrado com id: " + id));
        return ResponseEntity.ok().body(evento);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Evento> getEventoByNome(@PathVariable String nome) {
        return eventoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Integer id, @RequestBody Evento detalhesEventos) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nEvento não encontrado com id: " + id));

        evento.setNome(detalhesEventos.getNome());
        evento.setData(detalhesEventos.getData());
        evento.setLocal(detalhesEventos.getLocal());
        evento.setVagas(detalhesEventos.getVagas());

        Evento updatedEvento = eventoRepository.save(evento);
        return ResponseEntity.ok().body(updatedEvento);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventoById(@PathVariable Integer id) {
        eventoRepository.deleteById(id);
    }
}
