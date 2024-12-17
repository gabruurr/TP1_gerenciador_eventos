package com.programacaoweb.gerenciador_eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail não pode ser vazio.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotBlank(message = "O telefone não pode ser vazio.")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "tipo_pessoa_id", nullable = false)
    private TipoPessoa tipoPessoa;

    @ManyToMany(mappedBy = "pessoas", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();

    public Pessoa(String nome, String email, String telefone, TipoPessoa tipoPessoa) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
    }

    public String toStringParticipante() {
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

    public String toStringOrganizador() {
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
