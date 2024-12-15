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
public class Organizador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String telefone;

    @ManyToMany(mappedBy = "organizadores", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();

    public Organizador(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        StringBuilder eventosCadastrados = new StringBuilder();

        if (eventos.isEmpty()) {
            eventosCadastrados.append("Nenhum evento sob responsabilidade!\n");
        }
        else {
            for (Evento evento : eventos) {
                eventosCadastrados.append("\n- ").append(evento.getNome());
            }
        }
        return "\nNome: " + nome +
                "\nID: " + id +
                "\nE-mail: " + email +
                "\nNúmero de telefone: " + telefone +
                "\nEventos: " + eventosCadastrados;

    }
    }
