package com.app.mobilize.Pojo;

import java.util.Calendar;

public class ActivitatFinalitzada {private Calendar data;
    String username;
    int temps; // en segons
    double distancia;
    int tipus;
    double kcalCremades;

    public ActivitatFinalitzada(Calendar data, String username, int temps, double distancia, int tipus, double kcalCremades) {
        this.data = data;
        this.username = username;
        this.temps = temps;
        this.distancia = distancia;
        this.tipus = tipus;
        this.kcalCremades = kcalCremades;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
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