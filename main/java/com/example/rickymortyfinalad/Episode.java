package com.example.rickymortyfinalad;

public class Episode {
    private int id;
    private String name;
    private String air_date;
    private String episode;

    public Episode(int id, String name, String air_date, String episode){
        this.id = id;
        this.name = name;
        this.air_date = air_date;
        this.episode = episode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
