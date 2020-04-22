package com.app.sqliteopenhelper;

import java.io.Serializable;
import java.util.ArrayList;

public class Rutina implements Serializable {
    private String nom;
    private String info;
    private int nivell;
    private String modalitat;
    private String exercicis;

    public Rutina(String nom, String info,int nivell, String modalitat, String exercicis) {
        this.nom = nom;
        this.info = info;
        this.nivell = nivell;
        this.modalitat = modalitat;
        this.exercicis = exercicis;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public int getNivell() {
        return nivell;
    }

    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    public String getModalitat() {
        return modalitat;
    }

    public void setModalitat(String Modalitat) {
        this.modalitat = modalitat;
    }

    public String getExercicis() {
        return exercicis;

    }
}