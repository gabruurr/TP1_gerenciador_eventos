package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Pessoa;
import com.programacaoweb.gerenciador_eventos.repositories.PessoaRepository;
import com.programacaoweb.gerenciador_eventos.repositories.TipoPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizadores")
public class OrganizadorController {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAllOrganizador() {
        List<Pessoa> organizadores = pessoaRepository.findByTipoPessoa(tipoPessoaRepository.findById(2).get());
        return ResponseEntity.ok().body(organizadores);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> getOrganizadorById(@PathVariable Integer id) {
        Pessoa organizador = pessoaRepository.findByIdAndTipoPessoa(id, tipoPessoaRepository.findById(2).get()).orElseThrow(() ->
                new RuntimeException("\nOrganizador não encontrado com id: " + id));
        return ResponseEntity.ok().body(organizador);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Pessoa> getOrganizadorByNome(@PathVariable String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCaseAndTipoPessoa(nome, tipoPessoaRepository.findById(2).get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa createOrganizador(@RequestBody Pessoa organizador) {
        return pessoaRepository.save(organizador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updateOrganizador(@PathVariable Integer id, @RequestBody Pessoa detalhesOrganizador) {
        Pessoa organizador = pessoaRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nOrganizador não encontrado com id: " + id));

        organizador.setNome(detalhesOrganizador.getNome());
        organizador.setEmail(detalhesOrganizador.getEmail());
        organizador.setTelefone(detalhesOrganizador.getTelefone());

        Pessoa organizadorUpdated = pessoaRepository.save(organizador);
        return ResponseEntity.ok().body(organizadorUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizadorById(@PathVariable Integer id) {pessoaRepository.deleteById(id);
    }
}
