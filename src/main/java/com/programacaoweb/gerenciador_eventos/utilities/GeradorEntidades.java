package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.*;
import com.programacaoweb.gerenciador_eventos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GeradorEntidades {
    @Autowired
    ParticipanteRepository participanteRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private OrganizadorRepository organizadorRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public void gerarEntidades() {
        Participante participante = new Participante("Jose", "jose@gmail.com", "99000000");
        Participante participante2 = new Participante("Maria", "maria@gmail.com", "99777777");
        participanteRepository.save(participante);
        participanteRepository.save(participante2);

        String data1 = "12/12/2024 12:30";
        String data2 = "23/09/2024 17:00";
        Evento evento = new Evento("Feira de Ciências", LocalDateTime.parse(data1, Evento.fmtData), "Uespi, Piripiri", 50);
        Evento evento2 = new Evento("Exposição Artística", LocalDateTime.parse(data2, Evento.fmtData), "Museu de Teresina", 200);
        eventoRepository.save(evento);
        eventoRepository.save(evento2);

        Organizador organizador = new Organizador("Antonio", "antonio@gmail.com", "99666666");
        Organizador organizador2 = new Organizador("Fernanda", "fernanda@gmail.com", "99555555");
        organizadorRepository.save(organizador);
        organizadorRepository.save(organizador2);

        Servico servico = new Servico("Palestra", "Profissional contratado para dar uma palestra", 250.00);
        Servico servico2 = new Servico("Criticos", "Criticos especializados contratados para avaliar obras", 500.00);
        servicoRepository.save(servico);
        servicoRepository.save(servico2);
    }
}
