package com.valhalla.leandrolivveira.myapplication.model;

import com.google.firebase.database.DatabaseReference;
import com.valhalla.leandrolivveira.myapplication.helper.ConfiguracaoFirebase;

import java.io.Serializable;

public class Postagem implements Serializable {
    private long id;
    private String codigo;
    private String produto;
    private String quantidade;
    private String localidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}
