package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    //referencia para raiz () ou no que desejas.
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    //autenticacao de usuario no Firebase
    private FirebaseAuth usuario = FirebaseAuth.getInstance(); // recubera o objeto que permite manipular usuários


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //desloga o usuario
        //usuario.signOut();

        //Logar Usuario
        usuario.signInWithEmailAndPassword(
                 "fernando900@gmail.com", "fmx12345")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.i("SignUser", "Sucesso ao logar usuario");
                        } else {
                            Log.i("SignUser", "Erro ao logar usuario" + task.getException().getMessage());
                        }
                    }

                });


        //verificar se usuario está logado
/*
        if (usuario.getCurrentUser() != null) {

            Log.i("CurrentUser","Usuario logado");

        } else {
            Log.i("CurrentUser","Usuario não logado");
        };
*/


        /*cadastrar usuario */
 /*
        usuario.createUserWithEmailAndPassword
                ("fernando900@gmail.com", "fmx12345")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()) {
                            Log.i("CreateUser","Sucesso ao cadastrar usuario");
                        }else {
                            Log.i("CreateUser","Erro ao cadastrar usuario" + task.getException().getMessage() );
                        };
                    }
                });
*/

         /*
        DatabaseReference usuarios = referencia.child("usuarios");
        DatabaseReference produtos = referencia.child("produtos");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //referencia.child("pontos").setValue("200");

        //referencia.child("usuarios2").child("001").child("NOME").setValue("FERNANDO");


        DatabaseReference usuarios = referencia.child("usuarios");

        Usuario usuario = new Usuario();

        usuario.setNome("FER");
        usuario.setSobrenome("XAVIER");
        usuario.setIdade(25);

        usuarios.child("001").setValue(usuario);*/


        /*Salvar dados do nó produtos
        DatabaseReference produtos = referencia.child("produtos");
        Produto produto = new Produto();

        produto.setDescricao("IPHONE XXXX");
        produto.setMarca("iphone");
        produto.setPreco(100);

        produtos.child("001").setValue(produto);*/




    }
}
