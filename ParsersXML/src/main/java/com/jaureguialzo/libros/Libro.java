package com.jaureguialzo.libros;

public class Libro {

    private String titulo;
    private String autor;
    private String tema;

    public Libro(String titulo, String autor, String tema) {
        this.titulo = titulo;
        this.autor = autor;
        this.tema = tema;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", tema='" + tema + '\'' +
                '}';
    }

}
