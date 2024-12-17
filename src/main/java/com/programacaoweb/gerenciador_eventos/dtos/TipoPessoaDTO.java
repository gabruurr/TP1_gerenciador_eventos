package com.programacaoweb.gerenciador_eventos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Tipo_Pessoa", description = "Informações detalhadas sobre um tipo de pessoa")
public class TipoPessoaDTO {
    private String descricao;
}
