package com.valhalla.leandrolivveira.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.valhalla.leandrolivveira.myapplication.model.Postagem;

import java.util.ArrayList;
import java.util.List;

public class PostagemDAO implements IPostagemDAO {
    private SQLiteDatabase escreve, le;
    public PostagemDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Postagem postagem) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", postagem.getCodigo());
        cv.put("produto", postagem.getProduto());
        cv.put("quantidade", postagem.getQuantidade());
        cv.put("localidade", postagem.getLocalidade());

        try {
            escreve.insert(DbHelper.TABELA_POSTAGENS, null, cv );
            Log.i("INFO", "Postagem salva!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar postagem" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Postagem postagem) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", postagem.getCodigo());
        cv.put("produto", postagem.getProduto());
        cv.put("quantidade", postagem.getQuantidade());
        cv.put("localidade", postagem.getLocalidade());

        try {
            String[] args = {postagem.getCodigo().toString()};
            escreve.update(DbHelper.TABELA_POSTAGENS, cv, "codigo=?", args);
            Log.i("INFO", "Postagem atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar postagem!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Postagem postagem) {
        try {
            String[] args = {postagem.getCodigo().toString()};
            escreve.delete(DbHelper.TABELA_POSTAGENS, "codigo=?",args);
            Log.i("INFO", "Postagem removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover postagem!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Postagem> listar() {
        List<Postagem> postagens = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_POSTAGENS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            Postagem postagem = new Postagem();
            Long id = c.getLong(c.getColumnIndex("id"));
            String codigo = c.getString(c.getColumnIndex("codigo"));
            String produto = c.getString(c.getColumnIndex("produto"));
            String quantidade = c.getString(c.getColumnIndex("quantidade"));
            String localidade = c.getString(c.getColumnIndex("localidade"));

            postagem.setId(id);
            postagem.setCodigo(codigo);
            postagem.setProduto(produto);
            postagem.setQuantidade(quantidade);
            postagem.setLocalidade(localidade);

            postagens.add(postagem);
        }
        return postagens;
    }
}
