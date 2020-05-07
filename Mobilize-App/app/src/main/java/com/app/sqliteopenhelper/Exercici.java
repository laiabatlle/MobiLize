package com.app.sqliteopenhelper;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;

public class Exercici implements Parcelable {
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

    protected Exercici(Parcel in) {
        nom = in.readString();
        kmh = in.readString();
        durada_min = in.readInt();
        kcal = in.readDouble();
        pendent = in.readByte() != 0;
        musculs = in.readString();
        repeticions = in.readInt();
        series = in.readInt();
        tecnica = in.readString();
        nivell = in.readInt();
        modalitat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(kmh);
        dest.writeInt(durada_min);
        dest.writeDouble(kcal);
        dest.writeByte((byte) (pendent ? 1 : 0));
        dest.writeString(musculs);
        dest.writeInt(repeticions);
        dest.writeInt(series);
        dest.writeString(tecnica);
        dest.writeInt(nivell);
        dest.writeString(modalitat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Exercici> CREATOR = new Creator<Exercici>() {
        @Override
        public Exercici createFromParcel(Parcel in) {
            return new Exercici(in);
        }

        @Override
        public Exercici[] newArray(int size) {
            return new Exercici[size];
        }
    };

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