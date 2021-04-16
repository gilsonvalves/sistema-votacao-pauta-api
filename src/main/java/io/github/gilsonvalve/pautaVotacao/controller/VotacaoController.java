package io.github.gilsonvalve.pautaVotacao.controller;

import io.github.gilsonvalve.pautaVotacao.exception.ApiError;
import io.github.gilsonvalve.pautaVotacao.exception.ApiSucesso;
import io.github.gilsonvalve.pautaVotacao.exception.ResourseErroException;
import io.github.gilsonvalve.pautaVotacao.model.Associado;
import io.github.gilsonvalve.pautaVotacao.model.Pauta;
import io.github.gilsonvalve.pautaVotacao.model.Votacao;
import io.github.gilsonvalve.pautaVotacao.model.VotacaoStatus;
import io.github.gilsonvalve.pautaVotacao.repository.AssociadoRespository;
import io.github.gilsonvalve.pautaVotacao.repository.PautaRespository;
import io.github.gilsonvalve.pautaVotacao.repository.VotacaoRespository;
import io.github.gilsonvalve.pautaVotacao.repository.VotacaoStatusRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/votacao")
public class VotacaoController {

    @Autowired
    VotacaoRespository votacaoRespository;

    @Autowired
    VotacaoStatusRespository votacaoStatusRespository;

    @Autowired
    AssociadoRespository associadoRespository;

    @Autowired
    PautaRespository pautaRespository;

    @RequestMapping("/pautas/{id}/votacao")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> votacaoEstado(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1") long minuto){
        Votacao votacao = new Votacao();

    try{
        Optional<Pauta> pauta = pautaRespository.findById(id);
        if(!pauta.isPresent())
            throw new ResourseErroException("Pauta nao localizada");

        Optional<Votacao> votacaoPauta = votacaoRespository.findByPautaId(id);
        if(votacaoPauta.isPresent()){
            if(votacaoPauta.get().isOpen()) {
                throw new Exception("A Pauta "+id+" esta em votação nesse momento");
            } else {
                throw new Exception("A Pauta "+id+"já foi encerrada a votação");
            }
        }
        pauta.get().setOpenVotacao(true);
        votacao.setMinuto(minuto);
        votacao.setPatua(pautaRespository.save(pauta.get()));

    } catch(Exception e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new ApiError(
                HttpStatus.OK,
                "Erro ao iniciar sessão de votação!", e.getMessage()), HttpStatus.OK);
    }

    String formatDate = formatData(add_date_time(votacao.getInicioVotacao(),minuto));

        return  new ResponseEntity(new ApiSucesso(HttpStatus.CREATED,"Votacao ID: "+votacao.getId()+" iniciada com sucesso!!!"+formatDate),HttpStatus.CREATED);
    }

    @RequestMapping("/votacoes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Votacao getVotacao(@PathVariable Integer id){
        Optional<Votacao> votacao= votacaoRespository.findById(id);
        if(!votacao.isPresent()){
            throw new ResourseErroException("Votacao "+id+" não localizada");
        }
        return votacao.get();
    }

    @RequestMapping("/votacoes/{id}/associados/voto")
    @ResponseStatus(HttpStatus.OK)
    public boolean validarVotoAssociado(@PathVariable Integer id, @RequestBody Associado associadoId){
        Optional<Votacao> votacao = votacaoRespository.findById(id);
        if(!votacao.isPresent()){
            throw new ResourseErroException("Votacao "+id+" não localizada");
        }
        Optional<Integer> idAssociadoVoto = votacaoRespository.findAssociadoVotacaoId(id,associadoId.getId());

        return idAssociadoVoto.isPresent();
    }

    public  ResponseEntity<VotacaoStatus> votacaoStatus(@PathVariable Integer id){
        Optional<VotacaoStatus> votacaoStatus = votacaoStatusRespository.findByVotacaoid(id);
        Votacao votacao = votacaoStatus.get().getVotacao();

        if (!votacaoStatus.isPresent()){
            throw new ResourseErroException("A votação " + votacao.getId() + " não terminou! Dados não contabilizaos.");
        }
        return new ResponseEntity<>(votacaoStatus.get(), HttpStatus.OK);
    }




    public static  String formatData(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(date);
    }
    public static Date add_date_time(Date date, long minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, (int)minutes);
        return calendar.getTime();
    }
}
