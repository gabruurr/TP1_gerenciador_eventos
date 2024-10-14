package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Organizador;
import com.programacaoweb.gerenciador_eventos.entities.Servico;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import com.programacaoweb.gerenciador_eventos.repositories.OrganizadorRepository;
import com.programacaoweb.gerenciador_eventos.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ServicoMenu {
    Scanner sc = new Scanner(System.in);
    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private OrganizadorRepository organizadorRepository;

    public void gerenciarServicos() {
        int escolha;
        System.out.println("\nQue operação deseja realizar? ");
        System.out.println("1 - Cadastrar serviço");
        System.out.println("2 - Contratar serviço para evento");
        System.out.println("3 - Cancelar contrato");
        System.out.println("4 - Pesquisar serviço");
        System.out.println("5 - Deletar serviço");
        System.out.println("6 - Atualizar serviço");
        System.out.println("7 - Retornar à Página Inicial");
        escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                cadastrarServico();
                break;
            case 2:
                contratarServico();
                break;
            case 3:
                cancelarContratos();
                break;
            case 4:
                pesquisarServico();
                break;
            case 5:
                deletarServico();
                break;
            case 6:
                atualizarServico();
                break;
            case 7:
                return;
            default:
                System.out.println("Opção Inválida!");
        }
    }

    public void cadastrarServico() {
        System.out.print("Digite o nome do serviço a ser prestado: ");
        String nome = sc.nextLine();
        System.out.print("Descreva brevemente o tipo de serviço: ");
        String descricao = sc.nextLine();
        System.out.print("Digite o valor fixo do contrato: ");
        Double valorFixo = sc.nextDouble();
        sc.nextLine();
        Servico servico = new Servico(nome, descricao, valorFixo);

        servicoRepository.save(servico);
        System.out.println("Serviço de evento cadastrado com sucesso");
    }

    public void contratarServico() {
        System.out.print("Digite o ID do serviço a ser contratado: ");
        int idServico = sc.nextInt();
        sc.nextLine();
        Servico servicoTmp = servicoRepository.findById(idServico).get();

        if (servicoTmp.getEvento() != null) {
            System.out.println("Esse serviço já está sob contrato!");
            return;
        }

        System.out.print("Digite o ID do evento para o qual deseja esse serviço: ");
        int idEvento = sc.nextInt();
        sc.nextLine();
        Evento eventoTmp = eventoRepository.findById(idEvento).get();

        servicoTmp.setEvento(eventoTmp);
        eventoTmp.getServicos().add(servicoTmp);
        eventoRepository.save(eventoTmp);
        servicoRepository.save(servicoTmp);

        System.out.println("Serviço \"" + servicoTmp.getNome() + "\" contratado com sucesso!");
    }

    public void cancelarContratos() {
        System.out.println("Informe o ID do organizador responsável: ");
        int idOrganizador = sc.nextInt();
        sc.nextLine();
        Organizador organizadorResponsavel = organizadorRepository.findById(idOrganizador).get();

        List<Evento> eventosDoOrganizador = organizadorResponsavel.getEventos();

        if (eventosDoOrganizador.isEmpty()) {
            System.out.println("\nO organizador " + organizadorResponsavel.getNome() + " não é responsável por nenhum evento no momento!");
            System.out.println("Selecione um organizador válido e tente novamente.");
            return;
        }
        System.out.println("\nOs seguintes eventos de " + organizadorResponsavel.getNome() + " estão sob contratos:");
        for (Evento eventoTmp : eventosDoOrganizador) {
            if (!eventoTmp.getServicos().isEmpty()) {
                System.out.println("\n - " + eventoTmp.getNome() + ", ID: " + eventoTmp.getId());
            }
        }
        System.out.println("\nDigite o ID do evento que deseja ver mais detalhes: ");
        int idEvento = sc.nextInt();
        sc.nextLine();
        Evento evento = eventoRepository.findById(idEvento).get();

        System.out.println("\nServiços contratados para o evento \"" + evento.getNome() + "\" em " + evento.getLocal());
        for (Servico servicoTmp : evento.getServicos()) {
            System.out.println("\n - " + servicoTmp.getNome() + ", R$" + servicoTmp.getPreco() + " ---- ID: " + servicoTmp.getId());
        }
        System.out.println("Digite o ID do serviço a ser cancelado:");
        int idServico = sc.nextInt();
        sc.nextLine();

        Servico servicoSelecionado = servicoRepository.findById(idServico).get();

        evento.getServicos().remove(servicoSelecionado);
        eventoRepository.save(evento);

        servicoSelecionado.setEvento(null);
        servicoRepository.save(servicoSelecionado);

        System.out.println("Serviço cancelado com sucesso!");
    }

    public void pesquisarServico() {
        System.out.print("Digite do nome do serviço buscado: ");
        String nome = sc.nextLine();
        List<Servico> servicos = servicoRepository.findByNomeContainingIgnoreCase(nome);
        List<Servico> todosOsServicos = servicoRepository.findAll();
        if (nome == null) {
            todosOsServicos.forEach(System.out::println);
        }
        if (!servicos.isEmpty()) {
            servicos.forEach(System.out::println);
        } else {
            System.out.println("Desculpe, não encontramos esse serviço :(");
        }
    }

    public void deletarServico() {
        System.out.println("Digite o ID do serviço a ser deletado");
        int id = sc.nextInt();
        sc.nextLine();

        Servico servicotmp = servicoRepository.findById(id).get();
        if (servicotmp.getEvento() != null) {
            System.out.println("\nO serviço \"" + servicotmp.getNome() + "\" está sob contrato! ");
            System.out.println("Se deseja excluí-lo, cancele o contrato antes.");
            return;
        }
        servicoRepository.deleteById(id);
        System.out.println("Serviço deletado com sucesso!");
    }

    public void atualizarServico() {
        System.out.println("Digite o ID do serviço a ser atualizado: ");
        int idServico = sc.nextInt();
        sc.nextLine();

        Servico servicoTmp = servicoRepository.findById(idServico).get();

        System.out.println("Que tipo de operação deseja fazer?");
        System.out.println("1 - Nome");
        System.out.println("2 - Descrição");
        System.out.println("3 - Valor (somente se o serviço não estiver contratado)");
        System.out.println("4 - Retornar à Página Inicial");
        int escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
                System.out.println("Digite o novo nome do serviço: ");
                String novoNome = sc.nextLine();
                servicoTmp.setNome(novoNome);
                servicoRepository.save(servicoTmp);
                System.out.println("Nome alterado!");
                break;
            case 2:
                System.out.println("Digite a nova descrição do serviço: ");
                String novaDesc = sc.nextLine();
                servicoTmp.setNome(novaDesc);
                servicoRepository.save(servicoTmp);
                System.out.println("Descrição alterada!");
                break;
            case 3:
                System.out.println("Digite o novo valor do serviço");
                Double novoValor = sc.nextDouble();
                sc.nextLine();

                if (servicoTmp.getEvento() != null) {
                    System.out.println("Esse serviço está contratado! Não é possível alterar o valor fixo no momento.");
                    return;
                }
                servicoTmp.setPreco(novoValor);
                System.out.println("Preço alterado!");
                servicoRepository.save(servicoTmp);
                break;
            case 4:
                return;
            default:
                System.out.println("Entrada inválida!");
        }
    }
}

