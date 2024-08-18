package com.example.examen3_pmo189;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen3_pmo189.Contructores.Entrevista;
import java.util.List;
import java.util.ArrayList;

public class Entrevista_Lista extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Conexion entrevistaAdapter;
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

        entrevistaAdapter = new Conexion(this, listaEntrevistas);
        recyclerView.setAdapter(entrevistaAdapter);
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
                        Toast.makeText(Entrevista_Lista.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarRecyclerView() {
        if (!listaEntrevistas.isEmpty()) {
            entrevistaAdapter = new Conexion(this, listaEntrevistas);
            recyclerView.setAdapter(entrevistaAdapter);
        } else {
            Toast.makeText(Entrevista_Lista.this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
        }
    }
}
