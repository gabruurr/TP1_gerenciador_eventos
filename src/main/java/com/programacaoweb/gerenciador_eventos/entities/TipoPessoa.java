package com.programacaoweb.gerenciador_eventos.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
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
    private String descricao;

    public TipoPessoa(String descricao) {
        this.descricao = descricao;
    }
}
