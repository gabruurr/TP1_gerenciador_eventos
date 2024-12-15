package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.entities.Servico;
import com.programacaoweb.gerenciador_eventos.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Servico servico = servicoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nServiço não encontrado com id: " + id));
        return ResponseEntity.ok().body(servico);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Servico> getServicoByNome(@PathVariable String nome) {
        return servicoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servico createServico(@RequestBody Servico servico) {
        return servicoRepository.save(servico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> updateServico(@PathVariable Integer id, @RequestBody Servico detalhesServico) {
        Servico servico = servicoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nServiço não encontrado com id: " + id));

        servico.setNome(detalhesServico.getNome());
        servico.setDescricao(detalhesServico.getDescricao());
        servico.setPreco(detalhesServico.getPreco());

        Servico servicoUpdated = servicoRepository.save(servico);
        return ResponseEntity.ok().body(servicoUpdated);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServicoById(@PathVariable Integer id) {
        servicoRepository.deleteById(id);
    }
}