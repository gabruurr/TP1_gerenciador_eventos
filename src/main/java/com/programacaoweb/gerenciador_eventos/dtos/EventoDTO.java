package com.programacaoweb.gerenciador_eventos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "Evento", description = "Informações detalhadas de um evento existente")
public class EventoDTO {
    private Integer id;
    private String nome;
    private String local;
    private LocalDateTime data;
}
