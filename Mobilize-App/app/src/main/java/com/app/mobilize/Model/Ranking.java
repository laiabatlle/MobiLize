package com.app.mobilize.Model;

public class Ranking {
    private String username;
    private Long points;

    public Ranking(){};

    public Ranking(String username, Long points) {
        this.username = username;
       this.points = points;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
