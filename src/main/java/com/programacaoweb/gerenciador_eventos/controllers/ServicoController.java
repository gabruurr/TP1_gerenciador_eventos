package com.programacaoweb.gerenciador_eventos.controllers;

import com.programacaoweb.gerenciador_eventos.dtos.EventoDTO;
import com.programacaoweb.gerenciador_eventos.dtos.ServicoDTO;
import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Servico;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import com.programacaoweb.gerenciador_eventos.repositories.ServicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Operation(summary = "Listar todos os serviços", description = "Retorna todos os serviços cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços retornados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> findAllServico() {
        List<Servico> servicos = servicoRepository.findAll();
        List<ServicoDTO> servicoDTOs = new ArrayList<>();

        for (Servico servico : servicos) {
            List<EventoDTO> eventosDTO = new ArrayList<>();
            for (Evento evento : servico.getEventos()) {
                eventosDTO.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
            }
            servicoDTOs.add(new ServicoDTO(
                    servico.getId(),
                    servico.getNome(),
                    servico.getDescricao(),
                    servico.getPreco(),
                    eventosDTO
            ));
        }
        return ResponseEntity.ok(servicoDTOs);
    }

    @Operation(summary = "Buscar serviço por ID", description = "Retorna um serviço específico com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado."),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> getServicoById(@PathVariable Integer id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));
        List<EventoDTO> eventosDTO = new ArrayList<>();
        for (Evento evento : servico.getEventos()) {
            eventosDTO.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
        }

        ServicoDTO servicoDTO = new ServicoDTO(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getPreco(),
                eventosDTO
        );

        return ResponseEntity.ok(servicoDTO);
    }

    @Operation(summary = "Buscar serviços por nome", description = "Retorna serviços com base no nome parcial fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços encontrados."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ServicoDTO>> getServicoByNome(@PathVariable String nome) {
        List<Servico> servicos = servicoRepository.findByNomeContainingIgnoreCase(nome);
        List<ServicoDTO> servicoDTOs = new ArrayList<>();

        for (Servico servico : servicos) {
            List<EventoDTO> eventosDTO = new ArrayList<>();
            for (Evento evento : servico.getEventos()) {
                eventosDTO.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
            }

            servicoDTOs.add(new ServicoDTO(
                    servico.getId(),
                    servico.getNome(),
                    servico.getDescricao(),
                    servico.getPreco(),
                    eventosDTO
            ));
        }
        return ResponseEntity.ok(servicoDTOs);
    }

    @Operation(summary = "Criar novo serviço", description = "Cadastra um novo serviço.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PostMapping
    public ResponseEntity<ServicoDTO> createServico(@RequestBody ServicoDTO servicoDTO) {

        Servico servico = new Servico(servicoDTO.getNome(), servicoDTO.getDescricao(), servicoDTO.getPreco());
        Servico savedServico = servicoRepository.save(servico);

        List<EventoDTO> eventosDTO = new ArrayList<>();
        for (Evento evento : savedServico.getEventos()) {
            eventosDTO.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
        }

        ServicoDTO responseDTO = new ServicoDTO(
                savedServico.getId(),
                savedServico.getNome(),
                savedServico.getDescricao(),
                savedServico.getPreco(),
                eventosDTO
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @Operation(summary = "Atualizar serviço", description = "Atualiza os detalhes de um serviço existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> updateServico(@PathVariable Integer id, @RequestBody Servico detalhesServico) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));

        servico.setNome(detalhesServico.getNome());
        servico.setDescricao(detalhesServico.getDescricao());
        servico.setPreco(detalhesServico.getPreco());

        Servico updatedServico = servicoRepository.save(servico);

        ServicoDTO servicoDTO = new ServicoDTO(
                updatedServico.getId(),
                updatedServico.getNome(),
                updatedServico.getDescricao(),
                updatedServico.getPreco(),
                new ArrayList<>()
        );

        return ResponseEntity.ok(servicoDTO);
    }

    @Operation(summary = "Associar eventos a um serviço", description = "Associa uma lista de eventos a um serviço existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos associados ao serviço com sucesso."),
            @ApiResponse(responseCode = "404", description = "Serviço ou evento não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PatchMapping("/{id}/associar-eventos")
    public ResponseEntity<ServicoDTO> associateEventosToServico(@PathVariable Integer id, @RequestBody List<Integer> eventoIds) {

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));

        List<Evento> eventos = new ArrayList<>();
        for (Integer eventoId : eventoIds) {
            Evento evento = eventoRepository.findById(eventoId)
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado com id: " + eventoId));
            servico.getEventos().add(evento);
            evento.getServicos().add(servico);
        }

        Servico updatedServico = servicoRepository.save(servico);

        List<EventoDTO> eventosDTO = new ArrayList<>();
        for (Evento evento : updatedServico.getEventos()) {
            eventosDTO.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData()));
        }

        ServicoDTO responseDTO = new ServicoDTO(
                updatedServico.getId(),
                updatedServico.getNome(),
                updatedServico.getDescricao(),
                updatedServico.getPreco(),
                eventosDTO
        );

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Deletar serviço", description = "Remove um serviço pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Serviço deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServicoById(@PathVariable Integer id) {
        servicoRepository.deleteById(id);
    }
}