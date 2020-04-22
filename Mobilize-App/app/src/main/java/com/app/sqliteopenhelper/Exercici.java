package com.app.sqliteopenhelper;

import android.media.Image;

import java.sql.Time;

public class Exercici {
    private String nom;
    private String kmh;
    private int durada_min;
    private double kcal;
    private boolean pendent;
    private String musculs;
    private int repeticions;
    private int series;
    private String tecnica;
    private int nivell;
    private String modalitat;

    public Exercici(String nom, String kmh, int durada_min, double kcal, boolean pendent, String musculs, int repeticions, int series, String tecnica, int nivell, String modalitat) {

        this.nom = nom;
        this.modalitat = modalitat;
        this.nivell = nivell;
        this.musculs = musculs;
        this.tecnica = tecnica;
        this.series = series;
        this.repeticions = repeticions;
        this.kmh = kmh;
        this.durada_min = durada_min;
        this.kcal = kcal;
        this.pendent = pendent;

    }

    public String getNom() {
        return nom;
    }

    public String getKmh() {
        return kmh;
    }

    public String getMusculs() {
        return musculs;
    }

    public String getModalitat() {
        return modalitat;
    }

    public int getDuradamin() {
        return durada_min;
    }

    public int getRepeticions() {
        return repeticions;
    }

    public int getNivell() {
        return nivell;
    }

    public int getSeries() {
        return series;
    }

    public double getKcal() {
        return kcal;
    }

    public boolean getPendent() {
        return pendent;
    }

    public String getTecnica() {
        return tecnica;
    }

}