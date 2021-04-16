package io.github.gilsonvalve.pautaVotacao.controller;

import io.github.gilsonvalve.pautaVotacao.exception.ApiError;
import io.github.gilsonvalve.pautaVotacao.exception.ResourseErroException;
import io.github.gilsonvalve.pautaVotacao.model.Associado;
import io.github.gilsonvalve.pautaVotacao.model.Votacao;
import io.github.gilsonvalve.pautaVotacao.model.Voto;
import io.github.gilsonvalve.pautaVotacao.repository.AssociadoRespository;
import io.github.gilsonvalve.pautaVotacao.repository.VotacaoRespository;
import io.github.gilsonvalve.pautaVotacao.repository.VotoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/votacao")
public class VotoController {

    @Autowired
    AssociadoRespository associadoRespository;

    @Autowired
    VotacaoRespository votacaoRespository;

    @Autowired
    VotoRespository votoRespository;

    @RequestMapping("/pautas/{id}/votacao")
    public ResponseEntity sessaoVotacao (@RequestBody Associado associado, @PathVariable Integer idVotacao, @RequestParam(required = true)Boolean voto){
        Voto objVoto = new Voto();
            try{
                Optional<Votacao> votacao = votacaoRespository.findById(idVotacao);
                if(votacao.isPresent()){
                    throw  new ResourseErroException("Votacao " + idVotacao + " não localizada!");
                }
                if (!votacaoRespository.findVotacao(idVotacao)){
                    throw  new ResourseErroException("Votação Encerrada!");
                }
                Optional<Integer> idAssociadoVoto = votacaoRespository.findAssociadoVotacaoId(idVotacao,associado.getId());
                if (idAssociadoVoto.isPresent()){
                    throw new ResourseErroException("O Associado "+associado.getNome() + " Ja realizou o seu voto");
                }

                objVoto.setId(0);
                objVoto.setAssociado(associado);
                objVoto.setVoto(voto);
                objVoto = votoRespository.save(objVoto);

                votacao.get().getVotos().add(objVoto);
                votacaoRespository.save(votacao.get());
            }catch ( Exception e){
                return new ResponseEntity<>(new ApiError(
                        HttpStatus.FORBIDDEN,
                        "Não foi possível votar!", e.getMessage()), HttpStatus.FORBIDDEN);
            }
            return  new ResponseEntity<>(objVoto, HttpStatus.CREATED);
    }

    @RequestMapping("/votos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Voto buscarVoto(@PathVariable Integer id) {
        Optional<Voto> voto = votoRespository.findById(id);
        if (!voto.isPresent())
            throw new ResourseErroException("Voto " + id + " não encontrado!");
        return voto.get();
    }

}
