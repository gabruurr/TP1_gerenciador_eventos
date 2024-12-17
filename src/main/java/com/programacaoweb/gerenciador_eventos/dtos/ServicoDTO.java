package com.programacaoweb.gerenciador_eventos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(name = "Servico", description = "Informações detalhadas de um serviço oferecido")
public class ServicoDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;
    private List<EventoDTO> eventos;
}
