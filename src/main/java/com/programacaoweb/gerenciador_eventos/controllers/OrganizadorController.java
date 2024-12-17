package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.dtos.EventoDTO;
import com.programacaoweb.gerenciador_eventos.dtos.PessoaDTO;
import com.programacaoweb.gerenciador_eventos.entities.Evento;
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

@Tag(name = "Organizadores", description = "Endpoints para gerenciamento de pessoas organizadoras de eventos")
@RestController
@RequestMapping("/organizadores")
public class OrganizadorController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    @Operation(summary = "Listar todos os organizadores", description = "Retorna uma lista de todos os organizadores cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> findAllOrganizador() {
        List<Pessoa> organizadores = pessoaRepository.findByTipoPessoa(tipoPessoaRepository.findById(2).get());
        List<PessoaDTO> organizadoresDTO = new ArrayList<>();
        for (Pessoa pessoa : organizadores) {
            organizadoresDTO.add(new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone()));
        }
        return ResponseEntity.ok().body(organizadoresDTO);
    }

    @Operation(summary = "Buscar organizador por ID", description = "Retorna um organizador específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organizador encontrado."),
            @ApiResponse(responseCode = "404", description = "Organizador não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> getOrganizadorById(@PathVariable Integer id) {
        Pessoa organizador = pessoaRepository.findByIdAndTipoPessoa(id, tipoPessoaRepository.findById(2).get())
                .orElseThrow(() -> new RuntimeException("\nOrganizador não encontrado com id: " + id));
        PessoaDTO organizadorDTO = new PessoaDTO(organizador.getId(), organizador.getNome(), organizador.getEmail(), organizador.getTelefone());

        return ResponseEntity.ok().body(organizadorDTO);
    }

    @Operation(summary = "Buscar organizador por nome", description = "Retorna uma lista de organizadores que contenham o nome fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organizadores encontrados."),
            @ApiResponse(responseCode = "404", description = "Nenhum organizador encontrado com o nome fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<PessoaDTO>> getOrganizadorByNome(@PathVariable String nome) {
        List<Pessoa> organizadores = pessoaRepository.findByNomeContainingIgnoreCaseAndTipoPessoa(nome, tipoPessoaRepository.findById(2).get());
        List<PessoaDTO> organizadoresDTO = new ArrayList<>();
        for (Pessoa pessoa : organizadores) {
            organizadoresDTO.add(new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone()));
        }

        return ResponseEntity.ok().body(organizadoresDTO);
    }

    @Operation(summary = "Criar um novo organizador", description = "Cria um novo organizador no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizador criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")

    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaDTO> createOrganizador(@RequestBody Pessoa organizadorDTO) {
        Pessoa organizador = new Pessoa(organizadorDTO.getNome(), organizadorDTO.getEmail(), organizadorDTO.getTelefone(), tipoPessoaRepository.findById(2).get());
        Pessoa savedOrganizador = pessoaRepository.save(organizador);
        PessoaDTO organizadorDTO2 = new PessoaDTO(savedOrganizador.getId(), savedOrganizador.getNome(), savedOrganizador.getEmail(), savedOrganizador.getTelefone());

        return ResponseEntity.ok().body(organizadorDTO2);
    }

    @Operation(summary = "Atualizar parcialmente um organizador", description = "Atualiza apenas os campos fornecidos do organizador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campos do organizador atualizados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Organizador não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PessoaDTO> patchOrganizador(@PathVariable Integer id, @RequestBody PessoaDTO detalhesOrganizador) {
        Pessoa organizador = pessoaRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nOrganizador não encontrado com id: " + id));

        if (detalhesOrganizador.getNome() != null) organizador.setNome(detalhesOrganizador.getNome());
        if (detalhesOrganizador.getEmail() != null) organizador.setEmail(detalhesOrganizador.getEmail());
        if (detalhesOrganizador.getTelefone() != null) organizador.setTelefone(detalhesOrganizador.getTelefone());

        Pessoa organizadorUpdated = pessoaRepository.save(organizador);
        PessoaDTO organizadorDTO = new PessoaDTO(organizadorUpdated.getId(), organizadorUpdated.getNome(), organizadorUpdated.getEmail(), organizadorUpdated.getTelefone());

        return ResponseEntity.ok().body(organizadorDTO);
    }

    @Operation(summary = "Deletar organizador", description = "Remove um organizador com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Organizador deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Organizador não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizadorById(@PathVariable Integer id) {
        pessoaRepository.deleteById(id);
    }
}
