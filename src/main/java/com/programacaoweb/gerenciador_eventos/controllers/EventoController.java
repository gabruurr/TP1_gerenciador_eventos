package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.dtos.EventoDTO;
import com.programacaoweb.gerenciador_eventos.dtos.PessoaDTO;
import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Pessoa;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Operation(summary = "Listar todos os eventos", description = "Retorna uma lista de todos os eventos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<List<EventoDTO>> findAllEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        List<EventoDTO> eventosDTO = new ArrayList<>();

        for (Evento evento : eventos) {
            eventosDTO.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
        }

        return ResponseEntity.ok().body(eventosDTO);
    }

    @Operation(summary = "Buscar um evento por ID", description = "Retorna um evento específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventoDTO> getEventoById(@PathVariable Integer id) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nEvento não encontrado com id: " + id));
        EventoDTO eventoDTO = new EventoDTO(evento.getId(), evento.getNome(),evento.getLocal(), evento.getData());
        return ResponseEntity.ok().body(eventoDTO);
    }

    @Operation(summary = "Buscar evento por nome", description = "Retorna uma lista de eventos que contenham o nome fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados."),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado com o nome fornecido.")
    })
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<EventoDTO>> getEventoByNome(@PathVariable String nome) {
        List<Evento> eventos = eventoRepository.findByNomeContainingIgnoreCase(nome);
        List<EventoDTO> eventoDTOS = new ArrayList<>();
        for (Evento evento : eventos) {
            eventoDTOS.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
        }

        return ResponseEntity.ok().body(eventoDTOS);
    }
    @Operation(summary = "Criar um novo evento", description = "Cria um evento com os dados fornecidos no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping
    public ResponseEntity<EventoDTO> createEvento(@RequestBody Evento evento) {
        Evento savedEvento = eventoRepository.save(evento);
        EventoDTO eventoDTO = new EventoDTO(savedEvento.getId(), savedEvento.getNome(), savedEvento.getNome(), savedEvento.getData());
        return ResponseEntity.ok().body(eventoDTO);
    }

    @Operation(summary = "Atualizar um evento existente", description = "Atualiza os dados de um evento específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> updateEvento(@PathVariable Integer id, @RequestBody EventoDTO detalhesEventos) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("\nEvento não encontrado com id: " + id));

        evento.setNome(detalhesEventos.getNome());
        evento.setLocal(detalhesEventos.getLocal());
        evento.setData(detalhesEventos.getData());

        Evento updatedEvento = eventoRepository.save(evento);
        EventoDTO eventoDTO = new EventoDTO(updatedEvento.getId(), updatedEvento.getNome(), updatedEvento.getLocal(), updatedEvento.getData());
        return ResponseEntity.ok().body(eventoDTO);
    }

    @Operation(summary = "Deletar um evento por ID", description = "Remove um evento específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventoById(@PathVariable Integer id) {
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
