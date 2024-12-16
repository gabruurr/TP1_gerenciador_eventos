package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Pessoa;
import com.programacaoweb.gerenciador_eventos.repositories.PessoaRepository;
import com.programacaoweb.gerenciador_eventos.repositories.TipoPessoaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final PessoaRepository pessoaRepository;
    private final TipoPessoaRepository tipoPessoaRepository;

    public ParticipanteController(PessoaRepository pessoaRepository, TipoPessoaRepository tipoPessoaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tipoPessoaRepository = tipoPessoaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAllParticipante() {
        List<Pessoa> participantes = pessoaRepository.findByTipoPessoa(tipoPessoaRepository.findById(1).get());
        return ResponseEntity.ok().body(participantes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> getParticipanteById(@PathVariable Integer id) {
        Pessoa participante = pessoaRepository.findByIdAndTipoPessoa(id, tipoPessoaRepository.findById(1).get()).orElseThrow(() ->
                new RuntimeException("\nParticipante não encontrado com id: " + id));
        return ResponseEntity.ok().body(participante);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Pessoa> getParticipanteByNome(@PathVariable String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCaseAndTipoPessoa(nome, tipoPessoaRepository.findById(1).get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa createParticipante(@RequestBody Pessoa participante) {
        return pessoaRepository.save(participante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updateParticipante(@PathVariable Integer id, @RequestBody Pessoa detalhesParticipante) {
        Pessoa participante = pessoaRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nParticipante não encontrado com id: " + id));

        participante.setNome(detalhesParticipante.getNome());
        participante.setEmail(detalhesParticipante.getEmail());
        participante.setTelefone(detalhesParticipante.getTelefone());

        Pessoa participanteUpdated = pessoaRepository.save(participante);
        return ResponseEntity.ok().body(participanteUpdated);
    }

    @DeleteMapping("/nome/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipanteById(@PathVariable Integer id) {
        pessoaRepository.deleteById(id);
    }
}