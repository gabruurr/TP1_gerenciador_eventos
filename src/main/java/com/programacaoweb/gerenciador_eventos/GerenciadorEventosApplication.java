package com.programacaoweb.gerenciador_eventos;

import com.programacaoweb.gerenciador_eventos.entities.Evento;
import com.programacaoweb.gerenciador_eventos.entities.Participante;
import com.programacaoweb.gerenciador_eventos.repositories.EventoRepository;
import com.programacaoweb.gerenciador_eventos.repositories.ParticipanteRepository;
import com.programacaoweb.gerenciador_eventos.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Scanner;

@SpringBootApplication
public class GerenciadorEventosApplication implements CommandLineRunner {
    @Autowired
    private EventoMenu eventoMenu;
    @Autowired
    private ParticipanteMenu participanteMenu;
    @Autowired
    private OrganizadorMenu organizadorMenu;
    @Autowired
    private ServicoMenu servicoMenu;

    // gerar dados iniciais para agilizar, caso necessário, o momento da avaliação
    @Autowired
    private GeradorEntidades geradorEntidades;


    public static void main(String[] args) {
        SpringApplication.run(GerenciadorEventosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int escolha;
        boolean continuar = true;

        geradorEntidades.gerarEntidades();

        while (continuar) {
            System.out.println("\nPÁGINA INICIAL");
            System.out.println("1 - Eventos");
            System.out.println("2 - Participantes");
            System.out.println("3 - Organizadores");
            System.out.println("4 - Serviços de evento");
            System.out.println("5 - Fechar o Programa");

            System.out.print("Escolha uma opção: ");
            escolha = sc.nextInt();
            sc.nextLine();

            switch (escolha) {
                case 1:
                    eventoMenu.gerenciarEventos();
                    break;
                case 2:
                    participanteMenu.gerenciarParticipantes();
                    break;
                case 3:
                    organizadorMenu.gerenciarOrganizadores();
                    break;
                case 4:
                    servicoMenu.gerenciarServicos();
                    break;
                case 5:
                    System.out.println("Fechando o programa...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Entrada inválida!");

            }
        }
        sc.close();
        System.exit(0);
    }
}

