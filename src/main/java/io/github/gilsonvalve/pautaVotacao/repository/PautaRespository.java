package io.github.gilsonvalve.pautaVotacao.repository;

import io.github.gilsonvalve.pautaVotacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRespository extends JpaRepository<Pauta, Integer> {
}
