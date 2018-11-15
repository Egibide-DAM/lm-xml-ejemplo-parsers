package com.jaureguialzo.euskalmet;

public class Tendencia {

    private String fecha;

    private String viento;
    private String temperatura;
    private String tiempo;

    public Tendencia() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Tendencia {" +
                "\n  fecha='" + fecha + '\'' +
                "\n  temperatura='" + temperatura + '\'' +
                "\n  tiempo='" + tiempo + '\'' +
                "\n}";
    }

}
