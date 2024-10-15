package com.programacaoweb.gerenciador_eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    public Organizador() {
    }

    public Organizador(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Evento> getEventos() {
        return eventos;
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
