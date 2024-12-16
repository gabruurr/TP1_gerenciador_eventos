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
public class OrganizadorMenu {
    Scanner sc = new Scanner(System.in);

    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public void gerenciarOrganizadores() {
        int escolha;
        System.out.println("\nQue operação deseja realizar? ");
        System.out.println("1 - Cadastrar organizador");
        System.out.println("2 - Cadastrar um evento para o organizador");
        System.out.println("3 - Pesquisar organizador");
        System.out.println("4 - Deletar organizador");
        System.out.println("5 - Atualizar organizador");
        System.out.println("6 - Retornar à Página Inicial");
        escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                cadastrarOrganizadorMenu();
                break;
            case 2:
                cadastrarEventoOrganizadorMenu();
                break;
            case 3:
                pesquisarOrganizadorMenu();
                break;
            case 4:
                deletarOrganizadorMenu();
                break;
            case 5:
                atualizarOrganizadorMenu();
                break;
            case 6:
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void cadastrarOrganizadorMenu() {
        System.out.println("Digite o nome do organizador: ");
        String nome = sc.nextLine();
        System.out.println("Digite o e-mail do organizador: ");
        String email = sc.nextLine();
        System.out.println("Informe o numero de telefone:");
        String telefoneNumero = sc.nextLine();
        TipoPessoa organizador = tipoPessoaRepository.findById(2).get();
        Pessoa pessoa = new Pessoa(nome, email, telefoneNumero, organizador);
        pessoaRepository.save(pessoa);
        System.out.println("\nOrganizador cadastrado com sucesso!");
    }

    private void cadastrarEventoOrganizadorMenu() {
        System.out.print("Digite o ID do organizador: ");
        int idOrganizador = sc.nextInt();
        sc.nextLine();

        Pessoa pessoa = pessoaRepository.findById(idOrganizador).get();

        System.out.print("Digite o ID do evento: ");
        int idEvento = sc.nextInt();
        sc.nextLine();

        Evento evento = eventoRepository.findById(idEvento).get();

        evento.getPessoas().add(pessoa);
        pessoa.getTipoPessoa().setDescricao("Organizador");
        pessoa.getEventos().add(evento);

        pessoaRepository.save(pessoa);
        eventoRepository.save(evento);

        System.out.println("Agora esse organizador é responsável pelo evento \"" + evento.getNome() + "\"");
    }

    private void pesquisarOrganizadorMenu() {
        System.out.print("Digite do nome do organizador a ser buscado: ");
        String nome = sc.nextLine();
        TipoPessoa organizador = tipoPessoaRepository.findById(2).get();
        List<Pessoa> organizadores = pessoaRepository.findByNomeContainingIgnoreCaseAndTipoPessoa(nome, organizador);
        List<Pessoa> todosOrganizadores = pessoaRepository.findByTipoPessoa(organizador);

        if (nome == null) {
            for (Pessoa p : todosOrganizadores) {
                System.out.println(p.toStringOrganizador());
            }
        }
        if (!organizadores.isEmpty()) {
            for (Pessoa p : organizadores) {
                System.out.println(p.toStringOrganizador());
            }
        } else {
            System.out.println("Desculpe, não encontramos esse organizador :(");
        }
    }

    private void deletarOrganizadorMenu() {
        System.out.println("Digite o ID do organizador a ser deletado:");
        int idOrganizador = sc.nextInt();
        sc.nextLine();

        Pessoa organizador = pessoaRepository.findById(idOrganizador).get();

        for (Evento evento : organizador.getEventos()) {
            evento.getPessoas().remove(organizador);
            eventoRepository.save(evento);
        }

        pessoaRepository.delete(organizador);
        System.out.println("Organizador deletado com sucesso!");
    }

    private void atualizarOrganizadorMenu() {
        System.out.println("Digite o id do organizador a ser atualizado: ");
        int id = sc.nextInt();
        Pessoa organizadorEncontrado = pessoaRepository.findById(id).get();

        System.out.println("Qual campo deseja atualizar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - E-mail");
        System.out.println("3 - Telefone");
        System.out.println("4 - Eventos");
        System.out.println("5 - Retornar à Página Inicial");
        int escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                System.out.println("Digite o novo nome do organizador: ");
                String novoNome = sc.nextLine();
                organizadorEncontrado.setNome(novoNome);
                break;

            case 2:
                System.out.println("Digite o novo e-mail do organizador: ");
                String novoEmail = sc.nextLine();
                organizadorEncontrado.setEmail(novoEmail);
                break;

            case 3:
                System.out.println("Digite o novo telefone do organizador:");
                String novoTelefone = sc.nextLine();
                organizadorEncontrado.setTelefone(novoTelefone);
                break;

            case 4:
                removerGestaoEvento(organizadorEncontrado);
                break;

            case 5:
                return;

            default:
                System.out.println("Escolha inválida!");
        }
        pessoaRepository.save(organizadorEncontrado);
        System.out.println("Organizador atualizado com sucesso!");
    }

    private void removerGestaoEvento(Pessoa organizador) {
        if (organizador.getEventos().isEmpty()) {
            System.out.println("O organizador não é responsável por nenhum evento no momento!");
            return;
        }

        System.out.println("Eventos os quais o organizador é responsável:");
        for (int i = 0; i < organizador.getEventos().size(); i++) {
            System.out.println((i + 1) + " - " + organizador.getEventos().get(i).getNome());
        }

        System.out.print("Digite o ID do evento do qual deseja remover a gestão: ");
        int idEvento = sc.nextInt() - 1;
        sc.nextLine();

        if (idEvento < 0 || idEvento >= organizador.getEventos().size()) {
            System.out.println("Escolha inválida!");
            return;
        }

        Evento eventoSelecionado = organizador.getEventos().get(idEvento);

        eventoSelecionado.getPessoas().remove(organizador);
        organizador.getEventos().remove(eventoSelecionado);

        eventoRepository.save(eventoSelecionado);
        pessoaRepository.save(organizador);

        System.out.println("Organizador removido do evento com sucesso!");
    }
}
