package com.app.mobilize.Model;

import java.util.List;

public class Events {
    private String image;
    private String description;
    private String min_part;
    private String max_part;
    private String dateEvent;
    private List<String> inscripcionsList;

    public Events(){};

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMin_part() {
        return min_part;
    }

    public void setMin_part(String min_part) {
        this.min_part = min_part;
    }

    public String getMax_part() {
        return max_part;
    }

    public void setMax_part(String max_part) {
        this.max_part = max_part;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public List<String> getInscripcionsList() {
        return this.inscripcionsList;
    }

    public void setInscripcionsList(List<String> inscripcionsList) {
        this.inscripcionsList = inscripcionsList;
    }

}
