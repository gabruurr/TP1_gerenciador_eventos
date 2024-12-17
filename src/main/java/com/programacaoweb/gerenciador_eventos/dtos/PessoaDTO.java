package com.programacaoweb.gerenciador_eventos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Schema(name = "Pessoa", description = "Informações detalhadas de uma pessoa")
public class PessoaDTO {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
}
