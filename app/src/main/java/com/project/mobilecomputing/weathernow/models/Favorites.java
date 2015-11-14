package com.project.mobilecomputing.weathernow.models;

/**
 * Created by Kannan on 11/13/2015.
 * REFERENCE: http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class Favorites {
    private int id;
    private String location;

    public Favorites(){}

    public Favorites(String location) {
        super();
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "History [id=" + id + ", location=" + location + "]";
    }
}
