package io.github.gilsonvalve.pautaVotacao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Votacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Pauta patua;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioVotacao = new Date(System.currentTimeMillis());

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fimVotacao = new Date(System.currentTimeMillis());
    private long minuto = 1;

    @Column
    private boolean open = true;

    @JsonIgnore
    @OneToMany
    private List<Voto> votos;

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pauta getPatua() {
        return patua;
    }

    public void setPatua(Pauta patua) {
        this.patua = patua;
    }

    public Date getInicioVotacao() {
        return inicioVotacao;
    }

    public void setInicioVotacao(Date inicioVotacao) {
        this.inicioVotacao = inicioVotacao;
    }

    public Date getFimVotacao() {
        return fimVotacao;
    }

    public void setFimVotacao(Date fimVotacao) {
        this.fimVotacao = fimVotacao;
    }

    public long getMinuto() {
        return minuto;
    }

    public void setMinuto(long minuto) throws Exception {
        if (minuto > 1)
           throw new Exception("O tempo mínimo para sessão é de um 1 minutod");
            this.minuto = minuto;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
