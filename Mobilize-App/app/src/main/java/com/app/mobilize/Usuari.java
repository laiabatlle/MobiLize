package com.app.mobilize;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuari {
    private String username;
    private String password;
    private String email;
    private String gender;
    private String height;
    private String weight;
    private String dateNaixement;
    private Uri URLavatar;


    public Usuari (){};

    public Usuari (String username, String password, String email ) {
        this.username = username;
        this.password = password;
        this.email = email;
        gender = "";
        height = "0";
        weight = "0";
        dateNaixement = "mm/dd/aaaa";
       // URLavatar = Uri.parse("android.resource://com.app.mobilize/drawable/ic_user");
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDateNaixement() {
        return dateNaixement;
    }

    public void setDateNaixement(String dateNaixement) {
        this.dateNaixement = dateNaixement;
    }

    @Override
    public String toString() {
        return username;
    }

    public Uri getImage() {
        return URLavatar;
    }

    public void setImage(Uri imageUri) {
        this.URLavatar = imageUri;
    }
}
