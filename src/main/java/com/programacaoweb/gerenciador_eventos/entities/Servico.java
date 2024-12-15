package com.programacaoweb.gerenciador_eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Servico(String nome, String descricao, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    @Override
    public String toString() {
        String eventoContrato;
        if (evento == null) {
            eventoContrato = "Nenhum evento sob contrato\n";
        } else {
            eventoContrato = evento.getNome() + ", " + evento.getLocal();
        }
        return "\nID: " + id +
                "\nNome: " + nome +
                "\nDescrição: " + descricao +
                "\nValor do contrato: R$" + String.format("%.2f", preco) +
                "\nEventos: " + eventoContrato;

    }
}