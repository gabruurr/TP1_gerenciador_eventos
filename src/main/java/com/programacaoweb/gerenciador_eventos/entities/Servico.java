package com.programacaoweb.gerenciador_eventos.entities;

import jakarta.persistence.*;

@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Servico() {
    }

    public Servico(String nome, String descricao, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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