package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.Participante;
import com.programacaoweb.gerenciador_eventos.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParticipanteMenu {
    Scanner sc = new Scanner(System.in);
    @Autowired
    ParticipanteRepository participanteRepository;

    public void gerenciarParticipantes() {
        int escolha;
        System.out.println("\nQue operação deseja realizar? ");
        System.out.println("1 - Cadastrar participante");
        System.out.println("2 - Inscrever participante em evento");
        System.out.println("3 - Pesquisar participante");
        System.out.println("4 - Deletar participante");
        System.out.println("5 - Atualizar participante");
        System.out.println("6 - Retornar à Página Inicial");
        escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                cadastrarParticipanteMenu();
                break;
            case 3:
                pesquisarParticipanteMenu();
                break;
            case 4:


        }

    }

    public void cadastrarParticipanteMenu() {
        System.out.println("Digite o nome do participante: ");
        String nome = sc.nextLine();
        System.out.println("Digite o e-mail do participante: ");
        String email = sc.nextLine();
        System.out.println("Informe o numero de telefone:");
        String telefoneNumero = sc.nextLine();
        Participante participante = new Participante(nome, email, telefoneNumero);
        participanteRepository.save(participante);
        System.out.println("\nParticipante cadastrado com sucesso!");
    }

    public void pesquisarParticipanteMenu() {
        System.out.print("Digite do nome do participante a ser buscado: ");
        String nome = sc.nextLine();
        List<Participante> participantes = participanteRepository.findByNomeContainingIgnoreCase(nome);
        if (!participantes.isEmpty()) {
            participantes.forEach(System.out::println);
        } else {
            System.out.println("Desculpe, não encontramos esse participante :(");
        }
    }

    public void deletarParticipanteMenu() {
        System.out.println("Digite o ID do participante a ser deletado:");
        int id = sc.nextInt();
        participanteRepository.deleteById(id);
        System.out.println("Participante deletado com sucesso!");
    }
}
