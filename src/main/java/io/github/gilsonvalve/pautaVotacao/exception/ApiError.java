package io.github.gilsonvalve.pautaVotacao.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApiError {

    private Date timestamp = new Date(System.currentTimeMillis());
    private  HttpStatus status;
    private  String message;
    private List<String> erros;

    public ApiError(HttpStatus status, String message, List<String> erros) {
        this.status = status;
        this.message= message;
        this.erros = erros;
    }
    public ApiError(HttpStatus status, String message,String error){
        this.status = status;
        this.message = message;
        this.erros = Arrays.asList(error);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }
}
