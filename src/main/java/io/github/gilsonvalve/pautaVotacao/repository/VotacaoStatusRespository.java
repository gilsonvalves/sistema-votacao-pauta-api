package io.github.gilsonvalve.pautaVotacao.repository;

import io.github.gilsonvalve.pautaVotacao.model.VotacaoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotacaoStatusRespository extends JpaRepository<VotacaoStatus, Integer> {

    @Query("SELECT votacaoStatus FROM VotacaoStatus votacaoStatus JOIN votacaoStatus.votacao votacao"
            + "WHERE votacao.id = :idVotacao")
    public Optional<VotacaoStatus> findByVotacaoid(@Param("idVotacao") Integer idVotacao);

    @Query("SELECT new br.com.votacaoPautaServer.model.VotacaoStatus("
            + "votacao, SUM(CASE WHEN (voto.voto = true) THEN 1 ELSE 0 END) AS simQuant, "
            + "SUM(CASE WHEN (voto.voto = false) THEN 1 ELSE 0 END) AS naoQuant,"
            + "COUNT(voto.voto) AS votoQuant)"
            + "FROM Votacao votacao LEFT JOIN votacao.votos voto "
            + "WHERE votacao.id = :idVotacao" )
    public Optional<VotacaoStatus> votacaoContabilizada(@Param("idVotacao")Integer idVotacao);
}
