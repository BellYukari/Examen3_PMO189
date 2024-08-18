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

public class Entrevista_Audio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Audio Escuchar_audio_adapter;
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
                Intent intent = new Intent(Entrevista_Audio.this, MainActivity.class);
                startActivity(intent);
            }
        });

        obtenerListaEntrevistas();

         Audio = new Audio(this, listaEntrevistas);
        recyclerView.setAdapter(Audio);
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
                        Toast.makeText(Entrevista_Audio.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarRecyclerView() {
        if (!listaEntrevistas.isEmpty()) {
            if (Audio == null) {
                Audio = new Audio(this, listaEntrevistas);
                recyclerView.setAdapter(Audio);
            } else {
                Escuchar_audio_adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(Entrevista_Audio.this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
        }
    }
}
