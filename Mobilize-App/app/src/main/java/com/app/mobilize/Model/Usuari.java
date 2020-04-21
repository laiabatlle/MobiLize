package com.app.mobilize.Model;

import java.util.List;

public class Usuari {
    private String username;
    private String password;
    private String email;
    private String gender;
    private String height;
    private String weight;
    private String dateNaixement;
    private String URLavatar;
    private String privacity;
    private List<String> friendsList;

    public Usuari(){};

    public Usuari(String username, String password, String email ) {
        this.username = username;
        this.password = password;
        this.email = email;
        gender = "";
        height = "0";
        weight = "0";
        dateNaixement = "mm/dd/aaaa";
        privacity = "Pública";
        URLavatar = "https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/profileImages%2Fic_perfil.png?alt=media&token=740a1705-983a-4080-8642-0b50e3695322";
        friendsList = null;
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

    public String getPrivacity() {
        return privacity;
    }

    public void setPrivacity(String privacity) {
        this.privacity = privacity;
    }

    public String getImage() {
        return this.URLavatar;
    }

    public void setImage(String imageUri) {
        this.URLavatar = imageUri;
    }

    public List<String> getFriendsList() {
        return this.friendsList;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }


    @Override
    public String toString() {
        return username;
    }
}
