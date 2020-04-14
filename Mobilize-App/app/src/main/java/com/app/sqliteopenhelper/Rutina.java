package com.app.sqliteopenhelper;

public class Rutina {
    private String nom;
    private String info;
    private int nivell;
    private String modalitat;

    public Rutina(String nom, String info,int nivell, String modalitat) {
        this.nom = nom;
        this.info = info;
        this.nivell = nivell;
        this.modalitat = modalitat;
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
}