package io.github.gilsonvalve.pautaVotacao.repository;

import io.github.gilsonvalve.pautaVotacao.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotacaoRespository extends JpaRepository<Votacao, Integer> {

    @Query("SELECT open FROM Votacao votacao WHERE votacao.is = :idVotacao")
    public boolean findVotacao(@Param("idVotacao") Integer idVotacao);

    @Query("SELECT associado.id FROM Votacao votacao JOIN votacao.votos voto JOIN voto.associado associado \"\n" +
            "            + \"WHERE votacao.id = :idVotacao AND associado.id = :idAssociado")
    public Optional<Integer> findAssociadoVotacaoId(@Param("idVotacao") Integer idVotacao, @Param("idAssociado") Integer idAssociado);

    @Query("SELECT votacao FROM Votacao votacao JOIN votacao.pauta pauta WHERE pauta.id = :idPauta")
    public  Optional<Votacao> findByPautaId(@Param("idPauta") Integer idPauta);
}
