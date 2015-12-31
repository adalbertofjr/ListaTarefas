package com.adalbertofjr.listatarefas.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by AdalbertoF on 13/08/2015.
 */
public class Tarefas implements Serializable {


    private long id;
    private String titulo;
    private String dataVencimento;
    private int prioridade;
    private String observacao;
    private int encerrado;

    public Tarefas(){};

    public Tarefas(long id, String titulo, String dataVencimento, int prioridade, String observacao, int encerrado) {
        this.id = id;
        this.titulo = titulo;
        this.dataVencimento = dataVencimento;
        this.prioridade = prioridade;
        this.observacao = observacao;
        this.encerrado = encerrado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return getTitulo();
    }

    public int getEncerrado() {
        return encerrado;
    }

    public void setEncerrado(int encerrado) {
        this.encerrado = encerrado;
    }
}
