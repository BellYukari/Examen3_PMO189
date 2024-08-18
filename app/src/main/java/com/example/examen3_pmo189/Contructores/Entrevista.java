package com.example.examen3_pmo189.Contructores;

public class Entrevista {
    private String id;
    private String descripcion;
    private String periodista;
    private String fecha;
    private String imagenBase64;
    private String idFirestore;
    private String audioBase64;

    public Entrevista() {
        // Constructor vacío requerido por Firebase
    }

    public Entrevista(String id, String descripcion, String periodista, String fecha, String imagenBase64) {
        this.id = id;
        this.descripcion = descripcion;
        this.periodista = periodista;
        this.fecha = fecha;
        this.imagenBase64 = imagenBase64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPeriodista() {
        return periodista;
    }

    public void setPeriodista(String periodista) {
        this.periodista = periodista;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getIdFirestore() {
        return idFirestore;
    }

    public void setIdFirestore(String idFirestore) {
        this.idFirestore = idFirestore;
    }

    public String getAudioBase64() {
        return audioBase64;
    }

    public void setAudioBase64(String audioBase64) {
        this.audioBase64 = audioBase64;
    }
}
