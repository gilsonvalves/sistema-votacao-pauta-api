package io.github.gilsonvalve.pautaVotacao.controller;

import io.github.gilsonvalve.pautaVotacao.model.Associado;
import io.github.gilsonvalve.pautaVotacao.repository.AssociadoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/associado")
public class AssociadoController {

    private final AssociadoRespository associadoRep;

    @Autowired
    public AssociadoController(AssociadoRespository associadoRep) {
        this.associadoRep = associadoRep;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Associado salvar(@RequestBody @Valid Associado associado){
        return associadoRep.save(associado);
    }
}
