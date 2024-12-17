package com.programacaoweb.gerenciador_eventos.dtos;

import com.programacaoweb.gerenciador_eventos.dtos.EventoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ServicoDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;
    private List<EventoDTO> eventos;
}
