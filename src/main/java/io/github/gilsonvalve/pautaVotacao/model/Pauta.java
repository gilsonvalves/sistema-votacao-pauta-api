package io.github.gilsonvalve.pautaVotacao.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Pauta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "O campo titulo não pode ser vazio!")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "O campo descricao não pode ser nulo!")
    @NotEmpty(message = "O campo descricao não pode ser vazio!")
    @Column(columnDefinition="TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Associado associado;

    public boolean openVotacao = false;

    @Column(updatable = false)
    private LocalDate dataCadastro;

    @PrePersist
    public void prePersist(){
        setDataCadastro(LocalDate.now());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public boolean isOpenVotacao() {
        return openVotacao;
    }

    public void setOpenVotacao(boolean openVotacao) {
        this.openVotacao = openVotacao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
