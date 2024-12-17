package com.programacaoweb.gerenciador_eventos.utilities;

import com.programacaoweb.gerenciador_eventos.entities.*;
import com.programacaoweb.gerenciador_eventos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GeradorEntidades {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public void gerarEntidades() {

        TipoPessoa participante = new TipoPessoa("Participante");
        TipoPessoa organizador = new TipoPessoa("Organizador");
        tipoPessoaRepository.save(participante);
        tipoPessoaRepository.save(organizador);

        Pessoa pessoa = new Pessoa ("Jose", "jose@gmail.com", "99000000", participante);
        Pessoa pessoa2 = new Pessoa ("Maria", "maria@gmail.com", "99777777", participante);
        pessoaRepository.save(pessoa);
        pessoaRepository.save(pessoa2);

        String data1 = "12/08/2024 12:30";
        String data2 = "23/09/2024 17:00";
        Evento evento = new Evento("Feira de Ciências", LocalDateTime.parse(data1, Evento.fmtData), "Uespi, Piripiri", 50);
        Evento evento2 = new Evento("Exposição Artística", LocalDateTime.parse(data2, Evento.fmtData), "Museu de Teresina", 200);
        eventoRepository.save(evento);
        eventoRepository.save(evento2);

        Pessoa pessoa3 = new Pessoa("Antonio", "antonio@gmail.com", "99666666", organizador);
        Pessoa pessoa4 = new Pessoa("Fernanda", "fernanda@gmail.com", "99555555", organizador);
        pessoaRepository.save(pessoa3);
        pessoaRepository.save(pessoa4);

        Servico servico = new Servico("Palestra", "Profissional contratado para dar uma palestra", 250.00);
        Servico servico2 = new Servico("Criticos", "Criticos especializados contratados para avaliar obras", 500.00);
        servicoRepository.save(servico);
        servicoRepository.save(servico2);
    }
}
