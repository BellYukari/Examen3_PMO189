package com.example.examen3_pmo189;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen3_pmo189.Contructores.Entrevista;
import com.example.examen3_pmo189.Contructores.Contrc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Audio extends RecyclerView.Adapter<Audio.EscucharAudioViewHolder>{

    private Context context;
    private List<Entrevista> listaEntrevistas;
    private List<Boolean> seleccionados;

    public Audio(Context context, List<Entrevista> listaEntrevistas) {
        this.context = context;
        this.listaEntrevistas = listaEntrevistas;
        seleccionados = new ArrayList<>(Collections.nCopies(listaEntrevistas.size(), false));
    }

    @NonNull
    @Override
    public EscucharAudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio, parent, false);
        return new EscucharAudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EscucharAudioViewHolder holder, int position)  {
        Entrevista entrevista = listaEntrevistas.get(position);

        holder.txtId.setText(entrevista.getId());
        holder.txtDescripcion.setText(entrevista.getDescripcion());
        holder.textViewPeriodista.setText(entrevista.getPeriodista());
        holder.textViewFecha.setText(entrevista.getFecha());
        Bitmap imagenBitmap = Contrc.contrc.base64ToBitmap(entrevista.getImagenBase64());
        holder.imageViewEntrevista.setImageBitmap(imagenBitmap);

        holder.btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String audioBase64 = entrevista.getAudioBase64();
                reproducirAudioBase64(audioBase64);
            }
        });

        holder.itemView.setTag(entrevista.getIdFirestore());

        holder.itemView.setOnClickListener(v -> {
            seleccionados.set(position, !seleccionados.get(position));
            holder.itemView.setActivated(seleccionados.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listaEntrevistas.size();
    }

    private void reproducirAudioBase64(String audioBase64) {
        try {
            byte[] audioData = Base64.decode(audioBase64, Base64.DEFAULT);
            File outputFile = File.createTempFile("audio_temp", ".mp3", context.getCacheDir());
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(audioData);
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(outputFile.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class EscucharAudioViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtDescripcion, textViewFecha, textViewPeriodista;
        ImageView imageViewEntrevista;
        Button btnReproducir;

        public EscucharAudioViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar vistas
            txtId = itemView.findViewById(R.id.text_id);
            txtDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewPeriodista = itemView.findViewById(R.id.textViewPeriodista);
            imageViewEntrevista = itemView.findViewById(R.id.imageViewEntrevista);
            btnReproducir = itemView.findViewById(R.id.btnReproducir);
        }
    }
}
