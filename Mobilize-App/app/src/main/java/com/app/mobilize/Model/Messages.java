package com.app.mobilize.Model;

public class Messages {

    public String date, time, from, message;

    public Messages(){

    }

    public Messages(String date, String time, String from, String message) {
        this.date = date;
        this.time = time;
        this.from = from;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
