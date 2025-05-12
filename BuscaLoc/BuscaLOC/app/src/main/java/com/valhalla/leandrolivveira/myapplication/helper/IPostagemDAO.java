package com.valhalla.leandrolivveira.myapplication.helper;

import com.valhalla.leandrolivveira.myapplication.model.Postagem;

import java.util.List;

public interface IPostagemDAO {
    public boolean salvar(Postagem postagem);
    public boolean atualizar(Postagem postagem);
    public boolean deletar(Postagem postagem);
    public List<Postagem> listar();
}
