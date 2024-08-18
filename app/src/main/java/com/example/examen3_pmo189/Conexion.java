package com.example.examen3_pmo189;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen3_pmo189.Contructores.Entrevista;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.ArrayList;


public class Conexion extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Conexion conexion;
    private List<Entrevista> listaEntrevistas;
    Button btnRegresar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrevista_lista);

        btnRegresar = findViewById(R.id.btnRegresar);
        recyclerView = findViewById(R.id.recyclerViewEntrevistas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entrevista_Lista.this, MainActivity.class);
                startActivity(intent);
            }
        });

        obtenerListaEntrevistas();

        conexion = new Conexion(this,listaEntrevistas);
        recyclerView.setAdapter(conexion);
    }

    private void obtenerListaEntrevistas() {
        listaEntrevistas = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Entrevista")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Entrevista entrevista = document.toObject(Entrevista.class);
                            listaEntrevistas.add(entrevista);
                        }
                        actualizarRecyclerView();
                    } else {
                        Toast.makeText(Entrevista_List.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarRecyclerView() {
        if (!listaEntrevistas.isEmpty()) {
            conexion = new Conexion(this, listaEntrevistas);
            recyclerView.setAdapter(conexion);
        } else {
            Toast.makeText(Entrevista_List.this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
        }
    }



}
