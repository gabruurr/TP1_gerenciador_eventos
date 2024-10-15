package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Organizador;
import com.programacaoweb.gerenciador_eventos.repositories.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizadores")
public class OrganizadorController {

    @Autowired
    private OrganizadorRepository organizadorRepository;

    @GetMapping
    public ResponseEntity<List<Organizador>> findAllOrganizador() {
        List<Organizador> organizadores = organizadorRepository.findAll();
        return ResponseEntity.ok().body(organizadores);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Organizador> getOrganizadorById(@PathVariable Integer id) {
        Organizador organizador = organizadorRepository.findById(id).get();
        return ResponseEntity.ok().body(organizador);
    }

    @GetMapping(value = "/{nome}")
    public List<Organizador> getOrganizadorByNome(@PathVariable String nome) {
        return organizadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    public Organizador createOrganizador(@RequestBody Organizador organizador) {
        return organizadorRepository.save(organizador);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizadorById(@PathVariable Integer id) {
        organizadorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
