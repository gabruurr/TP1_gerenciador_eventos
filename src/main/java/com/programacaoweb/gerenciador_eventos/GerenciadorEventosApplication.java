package com.programacaoweb.gerenciador_eventos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class GerenciadorEventosApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorEventosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int escolha;

        System.out.println("PÁGINA INICIAL");
        System.out.println("1 - Eventos");
        System.out.println("2 - Participantes");
        System.out.println("3 - Organizadores");
        System.out.println("4 - Serviços prestados");
        System.out.println("5 - Fechar o Programa");

        System.out.print("Escolha uma opção: ");
        escolha = sc.nextInt();
        sc.nextLine();

    }
}

