package com.programacaoweb.gerenciador_eventos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "O nome do evento não pode ser vazio.")
    @Size(max = 100, message = "O nome do evento deve ter no máximo 100 caracteres.")
    private String nome;

    @NotNull(message = "A data do evento não pode ser nula.")
    private LocalDateTime data;

    @NotBlank(message = "O local do evento não pode ser vazio.")
    @Size(max = 200, message = "O local do evento deve ter no máximo 200 caracteres.")
    private String local;

    @Min(value = 1, message = "O número de vagas deve ser no mínimo 1.")
    @Max(value = 1000, message = "O número de vagas não pode ser maior que 1000.")
    private Integer vagas;

    @Min(value = 0, message = "O total de serviços deve ser no mínimo 0.")
    private Double total_servicos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "evento_servico", joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id"))
    private List<Servico> servicos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "evento_pessoa", joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id"))
    private List<Pessoa> pessoas = new ArrayList<>();


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
        } else {
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
