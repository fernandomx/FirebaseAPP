package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    //referencia para raiz () ou no que desejas.
    //private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    //autenticacao de usuario no Firebase
    //private FirebaseAuth usuario = FirebaseAuth.getInstance(); // recubera o objeto que permite manipular usuários


    //Upload de imagem através de um ImageView
    //adicionar a biblioteca para storage no build.gradle
    //implementation 'com.google.firebase:firebase-storage:19.1.0'
    //rules no firebase deverá estar aberta: true


    private ImageView imageFoto;
    private Button buttonUpload;
    private Button buttonApagarImagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpload = findViewById(R.id.buttonUpload);
        imageFoto = findViewById(R.id.imageFoto);
        buttonApagarImagem = findViewById(R.id.buttonApagarImagem);


        buttonApagarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Define nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens"); //pasta imagens

                StorageReference imagenRef = imagens.child("celular.jpeg");// nome da imagem que será apagada do firebase

                imagenRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this,"ERRO AO DELETAR" + e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"IMAGEM FOI APAGADA" ,Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });




        buttonUpload.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                //Configura para imagem ser salva em memoria
                                                imageFoto.setDrawingCacheEnabled(true);
                                                imageFoto.buildDrawingCache();

                                                //Recupera bitmap da imagem (imagea a ser carregada)
                                                Bitmap bitmap = imageFoto.getDrawingCache();

                                                //Comprimo bitmap para um formato png/jpeg
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bitmap.compress(Bitmap.CompressFormat.JPEG,75,baos);

                                                //Converte o baos para pixel brutos em uma matriz de bytes
                                                // (dados da imagem)
                                                byte[] dadosImagem = baos.toByteArray();

                                                //Define nós para o storage
                                                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                                                StorageReference imagens = storageReference.child("imagens"); //pasta imagens

                                                //nome da imagem
                                                String nomeArquivo = UUID.randomUUID().toString(); // nome de arquivo que não se repete
                                                StorageReference imagemRef = imagens.child(nomeArquivo +  ".jpeg"); //setvalue não é necessários


                                                //Retorna objeto que irá controlar o upload
                                                UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                                                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(MainActivity.this,"UPLOAD FALHOU" + e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                        //taskSnapshot.get

                                                        Toast.makeText(MainActivity.this,"UPLOAD SUCESSO" ,Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                            }
                                        });


                //DatabaseReference usuarios = referencia.child("usuarios");


                //DatabaseReference usuariopesquisa = usuarios.child("-M7OmTw1LrFARy_2WzpO");


                //nas regras do Firebase poderá ser criado indice
                //usuarios : {
                //    .indexon:["nome", "idade"]
                //}


                //Query usuariopesquisa = usuarios.orderByChild("nome").equalTo("Fernando");
                //Maior ou igual >=  e <=
                //Query usuariopesquisa = usuarios.orderByChild("idade").startAt(40);
                //Query usuariopesquisa = usuarios.orderByChild("idade").endAt(40);
                //      Query usuariopesquisa = usuarios.orderByChild("idade").startAt(20).endAt(40);

/*
        usuariopesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario dados = dataSnapshot.getValue(Usuario.class); //getvalue recebe um objeto do tipo classe

                Log.i("Dados usuario","nome " + dados.getNome());
                //Log.i("Dados usuario",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //criar novos usuários
        /*
        Usuario usuario = new Usuario();
        usuario.setNome("Renata");
        usuario.setSobrenome("Silva");
        usuario.setIdade(20);

        usuarios.push().setValue(usuario); // ira gerar um id unico
*/

                // Log.i("ERRO", "Erro " + this.getApplicationContext().getException().getMessage());

                //desloga o usuario
                //usuario.signOut();

                //Logar Usuario
   /*
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
