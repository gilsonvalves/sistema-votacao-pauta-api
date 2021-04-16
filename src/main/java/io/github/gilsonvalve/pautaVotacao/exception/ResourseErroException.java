package io.github.gilsonvalve.pautaVotacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;;

public class ResourseErroException  extends  RuntimeException{


    public ResourseErroException(String messange){
        super(messange);
    }
}
