package io.github.gilsonvalve.pautaVotacao.repository;

import io.github.gilsonvalve.pautaVotacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRespository extends JpaRepository<Voto, Integer> {
}
