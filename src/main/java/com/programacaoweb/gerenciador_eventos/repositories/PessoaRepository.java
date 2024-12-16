package com.programacaoweb.gerenciador_eventos.repositories;

import com.programacaoweb.gerenciador_eventos.entities.Pessoa;
import com.programacaoweb.gerenciador_eventos.entities.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    List<Pessoa> findByNomeContainingIgnoreCaseAndTipoPessoa(String nome, TipoPessoa tipoPessoa);
    List<Pessoa> findByTipoPessoa(TipoPessoa tipoPessoa);
    Optional<Pessoa> findByIdAndTipoPessoa(Integer id, TipoPessoa tipoPessoa);
}
