package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Servico;
import com.programacaoweb.gerenciador_eventos.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public ResponseEntity<List<Servico>> findAllServico() {
        List<Servico> servicos = servicoRepository.findAll();
        return ResponseEntity.ok().body(servicos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Integer id) {
        Servico servico = servicoRepository.findById(id).get();
        return ResponseEntity.ok().body(servico);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Servico> getServicoByNome(@PathVariable String nome) {
        return servicoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    public Servico createServico(@RequestBody Servico servico) {
        return servicoRepository.save(servico);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoById(@PathVariable Integer id) {
        servicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}