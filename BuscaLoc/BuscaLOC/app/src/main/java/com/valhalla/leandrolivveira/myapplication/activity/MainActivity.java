package com.valhalla.leandrolivveira.myapplication.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.valhalla.leandrolivveira.myapplication.R;
import com.valhalla.leandrolivveira.myapplication.adapter.PostagemAdapter;
import com.valhalla.leandrolivveira.myapplication.helper.ConfiguracaoFirebase;
import com.valhalla.leandrolivveira.myapplication.helper.DbHelper;
import com.valhalla.leandrolivveira.myapplication.helper.PostagemDAO;
import com.valhalla.leandrolivveira.myapplication.helper.RecyclerItemClickListener;
import com.valhalla.leandrolivveira.myapplication.model.Postagem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerPostagem;
    private PostagemAdapter postagemAdapter;
    private List<Postagem> listaPostagens = new ArrayList<>();
    private Postagem postagemSelecionada;
    private SearchView searchPostagem;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerPostagem = findViewById(R.id.recyclerPostagem);

        searchPostagem = findViewById(R.id.searchPostagem);
        searchPostagem.clearFocus();
        searchPostagem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtroLista(newText);
                return true;
            }
        });



        recyclerPostagem.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerPostagem,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                List<Postagem> listaPostagemAtualizada = postagemAdapter.getListaPostagem();
                                Postagem postagemSelecionada = listaPostagemAtualizada.get(position);

                                Intent intent = new Intent(MainActivity.this, PostagemActivity.class);
                                intent.putExtra("postagemSelecionada", postagemSelecionada);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                postagemSelecionada = listaPostagens.get(position);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a postagem: " + postagemSelecionada.getCodigo() + "?");
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PostagemDAO postagemDAO = new PostagemDAO(getApplicationContext());
                                        if (postagemDAO.deletar(postagemSelecionada)){
                                            carregarListaPostagem();
                                            Toast.makeText(getApplicationContext(),
                                                    "Sucesso ao excluir postagem!",
                                                    Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),
                                                    "erro ao excluir postagem!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostagemActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaPostagem(){
        PostagemDAO postagemDAO = new PostagemDAO(getApplicationContext());
        listaPostagens = postagemDAO.listar();

        postagemAdapter = new PostagemAdapter(listaPostagens);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPostagem.setLayoutManager(layoutManager);
        recyclerPostagem.setHasFixedSize(true);
        recyclerPostagem.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerPostagem.setAdapter(postagemAdapter);
    }

    @Override
    protected void onStart() {
        carregarListaPostagem();
        super.onStart();
    }

    private void filtroLista(String text){
        List<Postagem> listaFiltro = new ArrayList<>();
        for (Postagem postagem : listaPostagens){
            if (postagem.getCodigo().toLowerCase().contains(text.toLowerCase())){
                listaFiltro.add(postagem);
            }else if (postagem.getProduto().toLowerCase().contains(text.toLowerCase())){
                listaFiltro.add(postagem);
            }
        }

        if (listaFiltro.isEmpty()){
            Toast.makeText(this, "Nenhum item encontrado", Toast.LENGTH_SHORT).show();
        }else {
            postagemAdapter.setListaFiltro(listaFiltro);
        }
    }
}
