package com.app.mobilize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuari {
    String username;
    String password;
    String email;
    List<Integer> peso;
    String gender;
    int height;
    Date dateNaixement;

    public Usuari (String username, String password, String email ) {
        this.username = username;
        this.password = password;
        this.email = email;
        peso = new ArrayList<Integer>();
        gender = "";
        height = 0;
        dateNaixement = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getPeso() {
        return peso;
    }

    public void setPeso(List<Integer> peso) {
        this.peso = peso;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getDateNaixement() {
        return dateNaixement;
    }

    public void setDateNaixement(Date dateNaixement) {
        this.dateNaixement = dateNaixement;
    }
}
