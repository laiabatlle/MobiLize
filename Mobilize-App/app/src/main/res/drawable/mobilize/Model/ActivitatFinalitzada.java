package com.app.mobilize.Model;

public class ActivitatFinalitzada {
    String data;
    String username;
    long temps; // en segons
    double distancia;
    int tipus;
    double kcalCremades;

    public ActivitatFinalitzada(String data, String username, long temps, double distancia, int tipus, double kcalCremades) {
        this.data = data;
        this.username = username;
        this.temps = temps;
        this.distancia = distancia;
        this.tipus = tipus;
        this.kcalCremades = kcalCremades;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTemps() {
        return temps;
    }

    public void setTemps(long temps) {
        this.temps = temps;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getTipus() {
        return tipus;
    }

    public void setTipus(int tipus) {
        this.tipus = tipus;
    }

    public double getKcalCremades() {
        return kcalCremades;
    }

    public void setKcalCremades(double kcalCremades) {
        this.kcalCremades = kcalCremades;
    }
}