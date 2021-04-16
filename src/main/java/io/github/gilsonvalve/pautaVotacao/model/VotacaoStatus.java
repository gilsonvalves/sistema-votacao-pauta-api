package io.github.gilsonvalve.pautaVotacao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class VotacaoStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Votacao votacao;

    @Column(nullable = false)
    private long simQuant;

    @Column(nullable = false)
    private long naoQuant;

    @Column(nullable = false)
    private long votoQuant;

    @Column()
    private String resultado;

    public VotacaoStatus(Votacao votacao, long simQuant, long naoQuant, long votoQuant, String resultado) {
        this.votacao = votacao;
        this.simQuant = simQuant;
        this.naoQuant = naoQuant;
        this.votoQuant = votoQuant;
        this.resultado = resultado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Votacao getVotacao() {
        return votacao;
    }

    public void setVotacao(Votacao votacao) {
        this.votacao = votacao;
    }

    public long getSimQuant() {
        return simQuant;
    }

    public void setSimQuant(long simQuant) {
        this.simQuant = simQuant;
    }

    public long getNaoQuant() {
        return naoQuant;
    }

    public void setNaoQuant(long naoQuant) {
        this.naoQuant = naoQuant;
    }

    public long getVotoQuant() {
        return votoQuant;
    }

    public void setVotoQuant(long votoQuant) {
        this.votoQuant = votoQuant;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
