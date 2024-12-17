package com.programacaoweb.gerenciador_eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "servicos", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();

    public Servico(String nome, String descricao, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    @Override
    public String toString() {
        String eventoContrato = "";
        if (eventos.isEmpty()) {
            eventoContrato = "Nenhum evento sob contrato\n";
        } else {
            for (Evento evento : eventos) {
            eventoContrato = evento.getNome() + ", " + evento.getLocal();
            }
        }
        return "\nID: " + id +
                "\nNome: " + nome +
                "\nDescrição: " + descricao +
                "\nValor do contrato: R$" + String.format("%.2f", preco) +
                "\nEventos: " + eventoContrato;

    }
}