package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.dtos.TipoPessoaDTO;
import com.programacaoweb.gerenciador_eventos.entities.TipoPessoa;
import com.programacaoweb.gerenciador_eventos.repositories.TipoPessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-pessoa")
public class TipoPessoaController {

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    @Operation(summary = "Listar todos os tipos de pessoa", description = "Retorna uma lista com todos os tipos de pessoa cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<List<TipoPessoa>> findAllTiposPessoa() {
        List<TipoPessoa> tiposPessoa = tipoPessoaRepository.findAll();
        return ResponseEntity.ok(tiposPessoa);
    }

    @Operation(summary = "Buscar tipo de pessoa por ID", description = "Retorna um tipo de pessoa com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de pessoa encontrado."),
            @ApiResponse(responseCode = "404", description = "Tipo de pessoa não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoPessoa> getTipoPessoaById(@PathVariable Integer id) {
        TipoPessoa tipoPessoa = tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de Pessoa não encontrado com id: " + id));
        return ResponseEntity.ok(tipoPessoa);
    }

    @Operation(summary = "Criar um novo tipo de pessoa", description = "Cria um novo tipo de pessoa no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de pessoa criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoPessoa> createTipoPessoa(@RequestBody TipoPessoaDTO tipoPessoa) {
        TipoPessoa novoTipoPessoa = new TipoPessoa(tipoPessoa.getDescricao());
        return ResponseEntity.ok(novoTipoPessoa);
    }

    @Operation(summary = "Atualizar tipo de pessoa", description = "Atualiza um tipo de pessoa existente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de pessoa atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Tipo de pessoa não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TipoPessoa> updateTipoPessoa(@PathVariable Integer id, @RequestBody TipoPessoaDTO tipoPessoaDetalhes) {
        TipoPessoa tipoPessoa = tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de Pessoa não encontrado com id: " + id));

        tipoPessoa.setDescricao(tipoPessoaDetalhes.getDescricao());

        TipoPessoa updatedTipoPessoa = tipoPessoaRepository.save(tipoPessoa);
        return ResponseEntity.ok(updatedTipoPessoa);
    }

    @Operation(summary = "Deletar tipo de pessoa", description = "Remove um tipo de pessoa com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de pessoa deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Tipo de pessoa não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTipoPessoa(@PathVariable Integer id) {
        tipoPessoaRepository.deleteById(id);
    }
}
