package com.programacaoweb.gerenciador_eventos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventoDTO {
    private Integer id;
    private String nome;
    private String local;
    private LocalDateTime data;
}
