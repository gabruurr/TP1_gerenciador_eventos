package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Pessoa;
import com.programacaoweb.gerenciador_eventos.entities.TipoPessoa;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import com.programacaoweb.gerenciador_eventos.repositories.PessoaRepository;
import com.programacaoweb.gerenciador_eventos.repositories.TipoPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParticipanteMenu {
    Scanner sc = new Scanner(System.in);
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

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

    private void cadastrarParticipanteMenu() {
        System.out.println("Digite o nome do participante: ");
        String nome = sc.nextLine();
        System.out.println("Digite o e-mail do participante: ");
        String email = sc.nextLine();
        System.out.println("Informe o numero de telefone:");
        String telefoneNumero = sc.nextLine();
        TipoPessoa participante = tipoPessoaRepository.findById(1).get();
        Pessoa pessoa = new Pessoa(nome, email, telefoneNumero, participante);
        pessoaRepository.save(pessoa);
        System.out.println("\nParticipante cadastrado com sucesso!");
    }

    private void inscreverParticipanteEmEventoMenu() {
        System.out.print("Digite o ID do participante a ser inscrito: ");
        int idParticipante = sc.nextInt();
        sc.nextLine();

        Pessoa participante = pessoaRepository.findById(idParticipante).get();

        if (participante.getTipoPessoa().getId() != 1) {
            System.out.println("\nDigite o ID de um participante!");
            return;
        }

        System.out.print("Digite o ID do evento no qual deseja inscrever o participante: ");
        int idEvento = sc.nextInt();
        sc.nextLine();

        Evento evento = eventoRepository.findById(idEvento).get();

        if (evento.getVagas() <= 0) {
            System.out.println("Desculpe, o evento está cheio!");
            return;
        }

        evento.getPessoas().add(participante);
        participante.getEventos().add(evento);
        evento.setVagas(evento.getVagas() - 1);

        pessoaRepository.save(participante);
        eventoRepository.save(evento);

        System.out.println("Participante inscrito com sucesso!");
    }

    private void pesquisarParticipanteMenu() {
        System.out.print("Digite do nome do participante a ser buscado: ");
        String nome = sc.nextLine();
        TipoPessoa participante = tipoPessoaRepository.findById(1).get();

        List<Pessoa> participantes = pessoaRepository.findByNomeContainingIgnoreCaseAndTipoPessoa(nome, participante);
        List<Pessoa> todosParticipantes = pessoaRepository.findByTipoPessoa(participante);
        if (nome == null) {
            for (Pessoa p : todosParticipantes) {
                System.out.println(p.toStringParticipante());
            }
        }
        if (!participantes.isEmpty()) {
           for (Pessoa p : participantes) {
               System.out.println(p.toStringParticipante());
           }
        } else {
            System.out.println("Desculpe, não encontramos esse participante :(");
        }
    }

    private void deletarParticipanteMenu() {
        System.out.println("Digite o ID do participante a ser deletado:");
        int idParticipante = sc.nextInt();
        sc.nextLine();

        Pessoa participante = pessoaRepository.findById(idParticipante).get();

        if (participante.getTipoPessoa().getId() != 1) {
            System.out.println("\nDigite o ID de um participante!");
            return;
        }

        for (Evento evento : participante.getEventos()) {
            evento.getPessoas().remove(participante);
            evento.setVagas(evento.getVagas() + 1);
            eventoRepository.save(evento);
        }

        pessoaRepository.delete(participante);
        System.out.println("Participante deletado com sucesso!");
    }

    private void atualizarParticipanteMenu() {
        System.out.println("Digite o id do participante a ser atualizado: ");
        int id = sc.nextInt();
        Pessoa participanteEncontrado = pessoaRepository.findById(id).get();

        if (participanteEncontrado.getTipoPessoa().getId() != 1) {
            System.out.println("\nDigite o ID de um participante!");
            return;
        }

        System.out.println("Qual campo deseja atualizar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - E-mail");
        System.out.println("3 - Telefone");
        System.out.println("4 - Eventos inscritos");
        System.out.println("5 - Retornar à Página Inicial");
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

            case 5:
                return;

            default:
                System.out.println("Escolha inválida!");
        }
        pessoaRepository.save(participanteEncontrado);
        System.out.println("Participante atualizado com sucesso!");
    }

    private void removerInscricaoEvento(Pessoa participante) {
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

        eventoSelecionado.getPessoas().remove(participante);
        participante.getEventos().remove(eventoSelecionado);
        eventoSelecionado.setVagas(eventoSelecionado.getVagas() + 1);

        eventoRepository.save(eventoSelecionado);
        pessoaRepository.save(participante);

        System.out.println("Participante removido do evento com sucesso!");
    }
}
