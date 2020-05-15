package com.app.sqliteopenhelper;

public class Planning {

    private String nom;
    private String info;
    private int nivell;
    private int dies;
    private String modalitat;
    private String rutines;

    public Planning(String nom, String info,int nivell, int dies, String modalitat, String rutines) {
        this.nom = nom;
        this.info = info;
        this.nivell = nivell;
        this.dies = dies;
        this.modalitat = modalitat;
        this.rutines = rutines;
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

    public int getDies() {
        return dies;
    }

    public void setDies(int dies) {
        this.dies = dies;
    }

    public String getModalitat() {
        return modalitat;
    }

    public void setModalitat(String Modalitat) {
        this.modalitat = modalitat;
    }

    public String getRutines() {
        return rutines;

    }
}
