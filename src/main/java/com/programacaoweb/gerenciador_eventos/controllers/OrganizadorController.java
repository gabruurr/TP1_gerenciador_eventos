package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Organizador;
import com.programacaoweb.gerenciador_eventos.repositories.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Organizador organizador = organizadorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nOrganizador não encontrado com id: " + id));
        return ResponseEntity.ok().body(organizador);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Organizador> getOrganizadorByNome(@PathVariable String nome) {
        return organizadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Organizador createOrganizador(@RequestBody Organizador organizador) {
        return organizadorRepository.save(organizador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organizador> updateOrganizador(@PathVariable Integer id, @RequestBody Organizador detalhesOrganizador) {
        Organizador organizador = organizadorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nOrganizador não encontrado com id: " + id));

        organizador.setNome(detalhesOrganizador.getNome());
        organizador.setEmail(detalhesOrganizador.getEmail());
        organizador.setTelefone(detalhesOrganizador.getTelefone());

        Organizador organizadorUpdated = organizadorRepository.save(organizador);
        return ResponseEntity.ok().body(organizadorUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizadorById(@PathVariable Integer id) {
        organizadorRepository.deleteById(id);
    }
}
