package io.github.gilsonvalve.pautaVotacao.controller;

import io.github.gilsonvalve.pautaVotacao.model.Pauta;
import io.github.gilsonvalve.pautaVotacao.repository.PautaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pauta")
public class PautaController {

    private final PautaRespository pautaRespository;

    @Autowired
    public PautaController(PautaRespository pautaRespository) {
        this.pautaRespository = pautaRespository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pauta salvar(Pauta pauta){
        return pautaRespository.save(pauta);
    }


}
