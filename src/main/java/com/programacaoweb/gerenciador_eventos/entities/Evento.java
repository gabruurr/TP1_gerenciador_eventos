package com.programacaoweb.gerenciador_eventos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Evento {
    public static final DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private LocalDateTime data;
    private String local;
    private Integer vagas;
    private Double total_servicos;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER )
    private List<Servico> servicos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "evento_participante", joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id"))
    private List<Participante> participantes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "evento_organizador", joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "organizador_id"))
    private List<Organizador> organizadores = new ArrayList<>();


    public Evento(String nome, LocalDateTime data, String local, Integer vagas) {
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.vagas = vagas;
        this.total_servicos = 0.0;
    }

    @Override
    public String toString() {
        StringBuilder servicosContratados = new StringBuilder();

        if (servicos.isEmpty()) {
            servicosContratados.append("Nenhum serviço contratado");
        }
        else {
            for (Servico servico : servicos) {
                servicosContratados.append("\n- ").append(servico.getNome());
            }
        }
        return "\nNome do evento: " + nome +
                "\nID: " + id +
                "\nData e hora: " + data.format(fmtData) +
                "\nLocal: " + local +
                "\nVagas de participantes: " + vagas + "\n" +
                "\nAtividades: " + servicosContratados;
    }
}
