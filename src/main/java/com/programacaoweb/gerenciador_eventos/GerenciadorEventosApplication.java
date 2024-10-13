package com.programacaoweb.gerenciador_eventos;

import com.programacaoweb.gerenciador_eventos.utilities.EventoMenu;
import com.programacaoweb.gerenciador_eventos.utilities.ParticipanteMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class GerenciadorEventosApplication implements CommandLineRunner {
    @Autowired
    EventoMenu eventoMenu;
    @Autowired
    private ParticipanteMenu participanteMenu;

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorEventosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int escolha;
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nPÁGINA INICIAL");
            System.out.println("1 - Eventos");
            System.out.println("2 - Participantes");
            System.out.println("3 - Organizadores");
            System.out.println("4 - Serviços prestados");
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
                    System.out.println("em dev3");
                    break;
                case 4:
                    System.out.println("em dev4");
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

