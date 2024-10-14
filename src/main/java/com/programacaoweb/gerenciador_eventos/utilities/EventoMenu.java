package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class EventoMenu {
    Scanner sc = new Scanner(System.in);
    @Autowired
    EventoRepository eventoRepository;

    public void gerenciarEventos() {
        int escolha;
        System.out.println("\nQue operação deseja realizar? ");
        System.out.println("1 - Cadastrar evento");
        System.out.println("2 - Pesquisar evento");
        System.out.println("3 - Deletar evento");
        System.out.println("4 - Atualizar evento");
        System.out.println("5 - Retornar à Página Inicial");
        escolha = sc.nextInt();
        sc.nextLine();
        switch (escolha) {
            case 1:
                cadastrarEventoMenu();
                break;
            case 2:
                pesquisarEventoMenu();
                break;
            case 3:
                deletarEventoMenu();
                break;
            case 4:
                atualizarEventoMenu();
                break;
            case 5:
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public void cadastrarEventoMenu() {
        System.out.println("Digite o nome do evento: ");
        String nomeEvento = sc.nextLine();
        System.out.println("Digite a data do evento (DD/MM/YYYY): ");
        String dataEvento = sc.nextLine();
        System.out.println("Digite a hora do evento (HH:mm): ");
        String horaEvento = sc.nextLine();
        LocalDateTime data = LocalDateTime.parse(dataEvento + " " + horaEvento, Evento.fmtData);
        System.out.println("Informe o local do evento:");
        String local = sc.nextLine();
        System.out.println("Informe a capacidade total de participantes:");
        int cap = sc.nextInt();
        sc.nextLine();
        Evento evento = new Evento(nomeEvento, data, local, cap);
        eventoRepository.save(evento);
        System.out.println("\nEvento cadastrado com sucesso!");
    }

    public void pesquisarEventoMenu() {
        System.out.print("Digite do nome do evento a ser buscado: ");
        String nome = sc.nextLine();
        List<Evento> eventos = eventoRepository.findByNomeContainingIgnoreCase(nome);
        List<Evento> todosEventos = eventoRepository.findAll();
        if (nome == null) {
            todosEventos.forEach(System.out::println);
        }
        if (!eventos.isEmpty()) {
            eventos.forEach(System.out::println);
        } else {
            System.out.println("Desculpe, não encontramos esse evento :(");
        }
    }

    public void deletarEventoMenu() {
        System.out.println("Digite o ID do evento a ser deletado:");
        int id = sc.nextInt();
        Evento evento = eventoRepository.findById(id).get();
        if (!evento.getParticipantes().isEmpty()){
            System.out.println("Existem participantes inscritos nesse evento! Remova-os primeiro");
            return;
        }
        eventoRepository.deleteById(id);
        System.out.println("Evento deletado com sucesso!");
    }

    public void atualizarEventoMenu() {
        System.out.println("Digite o id do evento a ser atualizado: ");
        int id = sc.nextInt();
        Evento eventoEncontrado = eventoRepository.findById(id).get();

        System.out.println("Qual campo deseja atualizar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - Data");
        System.out.println("3 - Local");
        System.out.println("4 - Capacidade");
        System.out.println("5 - Retornar à Página Inicial");
        int escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                System.out.println("Digite o novo nome do evento:");
                String novoNome = sc.nextLine();
                eventoEncontrado.setNome(novoNome);
                break;

            case 2:
                System.out.println("Digite a nova data do evento (DD/MM/YYYY):");
                String novaData = sc.nextLine();
                System.out.println("Digite a nova hora do evento (HH:mm):");
                String novaHora = sc.nextLine();
                LocalDateTime data = LocalDateTime.parse(novaData + " " + novaHora, Evento.fmtData);
                eventoEncontrado.setData(data);
                break;

            case 3:
                System.out.println("Digite o novo local do evento:");
                String novoLocal = sc.nextLine();
                eventoEncontrado.setLocal(novoLocal);
                break;

            case 4:
                System.out.println("Digite a nova capacidade de participantes:");
                int novaCapacidade = sc.nextInt();
                sc.nextLine();
                eventoEncontrado.setCapacidade(novaCapacidade);
                break;

            case 5:
                return;

            default:
                System.out.println("Escolha inválida!");
        }
        eventoRepository.save(eventoEncontrado);
        System.out.println("Evento atualizado com sucesso!");
    }

}