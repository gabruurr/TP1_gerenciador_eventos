package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Organizador;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import com.programacaoweb.gerenciador_eventos.repositories.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class OrganizadorMenu {
    Scanner sc = new Scanner(System.in);
    @Autowired
    OrganizadorRepository organizadorRepository;
    @Autowired
    EventoRepository eventoRepository;

    public void gerenciarOrganizadors() {
        int escolha;
        System.out.println("\nQue operação deseja realizar? ");
        System.out.println("1 - Cadastrar organizador");
        System.out.println("2 - Inscrever organizador em evento");
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
                inscreverOrganizadorEmEventoMenu();
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

    public void cadastrarOrganizadorMenu() {
        System.out.println("Digite o nome do organizador: ");
        String nome = sc.nextLine();
        System.out.println("Digite o e-mail do organizador: ");
        String email = sc.nextLine();
        System.out.println("Informe o numero de telefone:");
        String telefoneNumero = sc.nextLine();
        Organizador organizador = new Organizador(nome, email, telefoneNumero);
        organizadorRepository.save(organizador);
        System.out.println("\nOrganizador cadastrado com sucesso!");
    }

    public void inscreverOrganizadorEmEventoMenu() {
        System.out.print("Digite o ID do organizador a ser inscrito: ");
        int idOrganizador = sc.nextInt();
        sc.nextLine();

        Organizador organizador = organizadorRepository.findById(idOrganizador).get();

        System.out.print("Digite o ID do evento no qual deseja inscrever o organizador: ");
        int idEvento = sc.nextInt();
        sc.nextLine();

        Evento evento = eventoRepository.findById(idEvento).get();

        if (evento.getCapacidade() <= 0) {
            System.out.println("Desculpe, o evento está cheio!");
            return;
        }

        evento.getOrganizadores().add(organizador);
        organizador.getEventos().add(evento);
        evento.setCapacidade(evento.getCapacidade() - 1);

        organizadorRepository.save(organizador);
        eventoRepository.save(evento);

        System.out.println("Organizador inscrito com sucesso!");
    }

    public void pesquisarOrganizadorMenu() {
        System.out.print("Digite do nome do organizador a ser buscado: ");
        String nome = sc.nextLine();
        List<Organizador> organizadors = organizadorRepository.findByNomeContainingIgnoreCase(nome);
        List<Organizador> todosOrganizadors = organizadorRepository.findAll();
        if (nome == null) {
            todosOrganizadors.forEach(System.out::println);
        }
        if (!organizadors.isEmpty()) {
            organizadors.forEach(System.out::println);
        } else {
            System.out.println("Desculpe, não encontramos esse organizador :(");
        }
    }

    public void deletarOrganizadorMenu() {
        System.out.println("Digite o ID do organizador a ser deletado:");
        int idOrganizador = sc.nextInt();
        sc.nextLine();

        Organizador organizador = organizadorRepository.findById(idOrganizador).get();

        for (Evento evento : organizador.getEventos()) {
            evento.getOrganizadores().remove(organizador);
            evento.setCapacidade(evento.getCapacidade() + 1);
            eventoRepository.save(evento);
        }

        organizadorRepository.delete(organizador);
        System.out.println("Organizador deletado com sucesso!");
    }

    public void atualizarOrganizadorMenu() {
        System.out.println("Digite o id do organizador a ser atualizado: ");
        int id = sc.nextInt();
        Organizador organizadorEncontrado = organizadorRepository.findById(id).get();

        System.out.println("Qual campo deseja atualizar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - E-mail");
        System.out.println("3 - Telefone");
        System.out.println("4 - Eventos inscritos");
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
                removerInscricaoEvento(organizadorEncontrado);
                break;

            default:
                System.out.println("Escolha inválida!");
        }
        organizadorRepository.save(organizadorEncontrado);
        System.out.println("Organizador atualizado com sucesso!");
    }

    private void removerInscricaoEvento(Organizador organizador) {
        if (organizador.getEventos().isEmpty()) {
            System.out.println("O organizador não está inscrito em nenhum evento.");
            return;
        }

        System.out.println("Eventos aos quais o organizador está inscrito:");
        for (int i = 0; i < organizador.getEventos().size(); i++) {
            System.out.println((i + 1) + " - " + organizador.getEventos().get(i).getNome());
        }

        System.out.print("Digite o ID do evento do qual deseja remover a inscrição: ");
        int idEvento = sc.nextInt() - 1;
        sc.nextLine();

        if (idEvento < 0 || idEvento >= organizador.getEventos().size()) {
            System.out.println("Escolha inválida!");
            return;
        }

        Evento eventoSelecionado = organizador.getEventos().get(idEvento);

        eventoSelecionado.getOrganizadores().remove(organizador);
        organizador.getEventos().remove(eventoSelecionado);
        eventoSelecionado.setCapacidade(eventoSelecionado.getCapacidade() + 1);

        eventoRepository.save(eventoSelecionado);
        organizadorRepository.save(organizador);

        System.out.println("Organizador removido do evento com sucesso!");
    }
}
