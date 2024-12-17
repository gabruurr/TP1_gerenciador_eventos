package com.programacaoweb.gerenciador_eventos.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Schema(name = "TipoPessoa", description = "Informações detalhadas da Entidade TipoPessoa")
public class TipoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "A descrição não pode ser vazia.")
    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres.")
    private String descricao;

    public TipoPessoa(String descricao) {
        this.descricao = descricao;
    }
}
