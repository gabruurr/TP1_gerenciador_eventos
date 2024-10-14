package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Participante;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
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
    @Autowired
    EventoRepository eventoRepository;

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
            case 2:
                inscreverParticipanteEmEventoMenu();
                break;
            case 3:
                pesquisarParticipanteMenu();
                break;
            case 4:
                deletarParticipanteMenu();
                break;
            case 5:
                atualizarParticipanteMenu();
                break;
            case 6:
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
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

    public void inscreverParticipanteEmEventoMenu() {
        System.out.print("Digite o ID do participante a ser inscrito: ");
        int idParticipante = sc.nextInt();
        sc.nextLine();

        Participante participante = participanteRepository.findById(idParticipante).get();

        System.out.print("Digite o ID do evento no qual deseja inscrever o participante: ");
        int idEvento = sc.nextInt();
        sc.nextLine();

        Evento evento = eventoRepository.findById(idEvento).get();

        if (evento.getParticipantes().size() >= evento.getCapacidade()) {
            System.out.println("Desculpe, o evento está cheio!");
            return;
        }

        evento.getParticipantes().add(participante);
        participante.getEventos().add(evento);

        participanteRepository.save(participante);
        eventoRepository.save(evento);

        System.out.println("Participante inscrito com sucesso!");
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
        Participante participante = participanteRepository.findById(id).get();
        removerInscricaoEvento(participante);
        participanteRepository.deleteById(id);
        System.out.println("Participante deletado com sucesso!");
    }

    public void atualizarParticipanteMenu() {
        System.out.println("Digite o id do participante a ser atualizado: ");
        int id = sc.nextInt();
        Participante participanteEncontrado = participanteRepository.findById(id).get();

        System.out.println("Qual campo deseja atualizar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - E-mail");
        System.out.println("3 - Telefone");
        System.out.println("4 - Eventos inscritos");
        int escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                System.out.println("Digite o novo nome do participante: ");
                String novoNome = sc.nextLine();
                participanteEncontrado.setNome(novoNome);
                break;

            case 2:
                System.out.println("Digite o novo e-mail do participante: ");
                String novoEmail = sc.nextLine();
                participanteEncontrado.setEmail(novoEmail);
                break;

            case 3:
                System.out.println("Digite o novo telefone do participante:");
                String novoTelefone = sc.nextLine();
                participanteEncontrado.setTelefone(novoTelefone);
                break;

            case 4:
                removerInscricaoEvento(participanteEncontrado);
                break;

            default:
                System.out.println("Escolha inválida!");
        }
        participanteRepository.save(participanteEncontrado);
        System.out.println("Participante atualizado com sucesso!");
    }

    private void removerInscricaoEvento(Participante participante) {
        if (participante.getEventos().isEmpty()) {
            System.out.println("O participante não está inscrito em nenhum evento.");
            return;
        }

        System.out.println("Eventos aos quais o participante está inscrito:");
        for (int i = 0; i < participante.getEventos().size(); i++) {
            System.out.println((i + 1) + " - " + participante.getEventos().get(i).getNome());
        }

        System.out.print("Digite o ID do evento do qual deseja remover a inscrição: ");
        int idEvento = sc.nextInt() - 1;
        sc.nextLine();

        if (idEvento < 0 || idEvento >= participante.getEventos().size()) {
            System.out.println("Escolha inválida!");
            return;
        }

        Evento eventoSelecionado = participante.getEventos().get(idEvento);

        eventoSelecionado.getParticipantes().remove(participante);
        participante.getEventos().remove(eventoSelecionado);

        eventoRepository.save(eventoSelecionado);
        participanteRepository.save(participante);

        System.out.println("Participante removido do evento com sucesso!");
    }
}
