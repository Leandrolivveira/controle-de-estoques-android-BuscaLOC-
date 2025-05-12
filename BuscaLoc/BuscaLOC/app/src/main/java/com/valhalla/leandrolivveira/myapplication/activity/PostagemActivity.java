package com.valhalla.leandrolivveira.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.valhalla.leandrolivveira.myapplication.R;
import com.valhalla.leandrolivveira.myapplication.helper.ConfiguracaoFirebase;
import com.valhalla.leandrolivveira.myapplication.helper.PostagemDAO;
import com.valhalla.leandrolivveira.myapplication.model.Postagem;

public class PostagemActivity extends AppCompatActivity {
    private TextInputEditText campoCod, campoProd, campoQtde, campoLoc;
    private Postagem postagemAtual;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);

        campoCod = findViewById(R.id.editCod);
        campoProd = findViewById(R.id.editProd);
        campoQtde = findViewById(R.id.editQtde);
        campoLoc = findViewById(R.id.editLoc);
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();

        postagemAtual = (Postagem) getIntent().getSerializableExtra("postagemSelecionada");

        if (postagemAtual != null){
            campoCod.setText(postagemAtual.getCodigo());
            campoProd.setText(postagemAtual.getProduto());
            campoQtde.setText(postagemAtual.getQuantidade());
            campoLoc.setText(postagemAtual.getLocalidade());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_postagem, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar :
                PostagemDAO postagemDAO = new PostagemDAO(getApplicationContext());

                if (postagemAtual != null){
                    String codigoPostagem = campoCod.getText().toString();
                    String produtoPostagem = campoProd.getText().toString();
                    String quantidadePostagem = campoQtde.getText().toString();
                    String localidadePostagem = campoLoc.getText().toString();

                    Postagem postagem = new Postagem();
                    postagem.setCodigo(codigoPostagem);
                    postagem.setProduto(produtoPostagem);
                    postagem.setQuantidade(quantidadePostagem);
                    postagem.setLocalidade(localidadePostagem);

                    if (postagemDAO.atualizar(postagem)){
                        finish();
                        Toast.makeText(getApplicationContext(),
                                "Sucesso ao atualizar postagem!",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Sucesso ao atualizar postagem!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    String codigoPostagem = campoCod.getText().toString();
                    String produtoPostagem = campoProd.getText().toString();
                    String quantidadePostagem = campoQtde.getText().toString();
                    String localidadePostagem = campoLoc.getText().toString();
                    Postagem postagem = new Postagem();
                    postagem.setCodigo(codigoPostagem);
                    postagem.setProduto(produtoPostagem);
                    postagem.setQuantidade(quantidadePostagem);
                    postagem.setLocalidade(localidadePostagem);

                    if (postagemDAO.salvar(postagem)){
                        finish();
                        Toast.makeText(getApplicationContext(),
                                "Sucesso ao salvar postagem!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "erro ao salvar postagem!",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                break;

            case R.id.itemSair :
                deslogarUsuario();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try {
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
