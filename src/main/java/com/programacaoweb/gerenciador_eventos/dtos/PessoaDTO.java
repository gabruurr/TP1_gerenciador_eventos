package com.programacaoweb.gerenciador_eventos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class PessoaDTO {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
}
