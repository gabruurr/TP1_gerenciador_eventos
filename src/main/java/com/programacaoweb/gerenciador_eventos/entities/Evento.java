package com.programacaoweb.gerenciador_eventos.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Evento {
    public static final DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private LocalDateTime data;
    private String local;
    private Integer capacidade;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Servico> servicos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "evento_participante", joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id"))
    private List<Participante> participantes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "evento_organizador", joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "organizador_id"))
    private List<Organizador> organizadores = new ArrayList<>();


    public Evento() {
    }

    public Evento(String nome, LocalDateTime data, String local, Integer capacidade) {
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.capacidade = capacidade;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public List<Organizador> getOrganizadores() {
        return organizadores;
    }
}
