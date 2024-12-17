package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.dtos.PessoaDTO;
import com.programacaoweb.gerenciador_eventos.entities.Pessoa;
import com.programacaoweb.gerenciador_eventos.repositories.PessoaRepository;
import com.programacaoweb.gerenciador_eventos.repositories.TipoPessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Participantes", description = "Endpoints para gerenciamento de pessoas participantes de eventos")
@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    @Operation(summary = "Listar todos os participantes", description = "Retorna uma lista de todos os participantes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> findAllParticipante() {
        List<Pessoa> participantes = pessoaRepository.findByTipoPessoa(tipoPessoaRepository.findById(1).get());
        List<PessoaDTO> participantesDTO = new ArrayList<>();
        for (Pessoa pessoa : participantes) {
            participantesDTO.add(new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone()));
        }
        return ResponseEntity.ok(participantesDTO);
    }

    @Operation(summary = "Buscar participante por ID", description = "Retorna um participante específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante encontrado."),
            @ApiResponse(responseCode = "404", description = "Participante não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getParticipanteById(@PathVariable Integer id) {
        Pessoa participante = pessoaRepository.findByIdAndTipoPessoa(id, tipoPessoaRepository.findById(1).get())
                .orElseThrow(() -> new RuntimeException("Participante não encontrado com id: " + id));
        PessoaDTO participanteDTO = new PessoaDTO(participante.getId(), participante.getNome(), participante.getEmail(), participante.getTelefone());

        return ResponseEntity.ok(participanteDTO);
    }

    @Operation(summary = "Buscar participante por nome", description = "Retorna uma lista de participantes com base no nome fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participantes encontrados."),
            @ApiResponse(responseCode = "404", description = "Nenhum participante encontrado com o nome fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<PessoaDTO>> getParticipanteByNome(@PathVariable String nome) {
        List<Pessoa> participantes = pessoaRepository.findByNomeContainingIgnoreCaseAndTipoPessoa(nome, tipoPessoaRepository.findById(1).get());
        List<PessoaDTO> participantesDTO = new ArrayList<>();
        for (Pessoa pessoa : participantes) {
            participantesDTO.add(new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone()));
        }
        return ResponseEntity.ok(participantesDTO);
    }

    @Operation(summary = "Criar um novo participante", description = "Cria um novo participante no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participante criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaDTO> createParticipante(@RequestBody PessoaDTO participanteDTO) {
        Pessoa participante = new Pessoa(participanteDTO.getNome(), participanteDTO.getEmail(), participanteDTO.getTelefone(), tipoPessoaRepository.findById(1).get());
        Pessoa savedParticipante = pessoaRepository.save(participante);
        PessoaDTO participanteDTO2 = new PessoaDTO(savedParticipante.getId(), savedParticipante.getNome(), savedParticipante.getEmail(), savedParticipante.getTelefone());

        return ResponseEntity.ok(participanteDTO2);
    }

    @Operation(summary = "Atualizar parcialmente um participante", description = "Atualiza apenas os campos fornecidos do participante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campos do participante atualizados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Participante não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PessoaDTO> patchParticipante(@PathVariable Integer id, @RequestBody PessoaDTO detalhesParticipante) {
        Pessoa participante = pessoaRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Participante não encontrado com id: " + id));

        if (detalhesParticipante.getNome() != null) participante.setNome(detalhesParticipante.getNome());
        if (detalhesParticipante.getEmail() != null) participante.setEmail(detalhesParticipante.getEmail());
        if (detalhesParticipante.getTelefone() != null) participante.setTelefone(detalhesParticipante.getTelefone());

        Pessoa updatedParticipante = pessoaRepository.save(participante);
        PessoaDTO participanteDTO = new PessoaDTO(updatedParticipante.getId(), updatedParticipante.getNome(), updatedParticipante.getEmail(), updatedParticipante.getTelefone());

        return ResponseEntity.ok(participanteDTO);
    }

    @Operation(summary = "Deletar participante", description = "Remove um participante com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Participante deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Participante não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipanteById(@PathVariable Integer id) {
        pessoaRepository.deleteById(id);
    }
}
