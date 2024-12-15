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
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String telefone;

    @ManyToMany(mappedBy = "participantes", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();

    public Participante(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        StringBuilder eventosInscritos = new StringBuilder();

        if (eventos.isEmpty()) {
            eventosInscritos.append("Nenhum evento inscrito");
        }
        else {
            for (Evento evento : eventos) {
                eventosInscritos.append("\n- ").append(evento.getNome());
            }
        }
        return "\nNome: " + nome +
                "\nID: " + id +
                "\nE-mail: " + email +
                "\nNúmero de telefone: " + telefone +
                "\nEventos inscritos: " + eventosInscritos;

    }
}