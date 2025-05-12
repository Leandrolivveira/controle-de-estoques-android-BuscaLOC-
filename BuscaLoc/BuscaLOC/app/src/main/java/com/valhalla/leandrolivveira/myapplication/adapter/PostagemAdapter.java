package com.valhalla.leandrolivveira.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valhalla.leandrolivveira.myapplication.R;
import com.valhalla.leandrolivveira.myapplication.model.Postagem;

import java.util.List;

public class PostagemAdapter extends RecyclerView.Adapter<PostagemAdapter.MyViewHolder> {
    private List<Postagem> listaPostagem;
    public PostagemAdapter(List<Postagem> lista) {
        this.listaPostagem = lista;
    }

    public void setListaFiltro(List<Postagem> listaFiltro){
        this.listaPostagem = listaFiltro;
        notifyDataSetChanged();
    }

    public List<Postagem> getListaPostagem(){
        return this.listaPostagem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_postagem_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Postagem postagem = listaPostagem.get(position);
        holder.cod.setText(postagem.getCodigo());
        holder.prod.setText(postagem.getProduto());
        holder.qtde.setText(postagem.getQuantidade());
        holder.loc.setText(postagem.getLocalidade());

    }

    @Override
    public int getItemCount() {
        return this.listaPostagem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cod, prod, qtde, loc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cod = itemView.findViewById(R.id.textCod);
            prod = itemView.findViewById(R.id.textProd);
            qtde = itemView.findViewById(R.id.textQtde);
            loc = itemView.findViewById(R.id.textLoc);
        }
    }
}
