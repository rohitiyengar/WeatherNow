package com.project.mobilecomputing.weathernow.models;

/**
 * Created by Kannan on 11/13/2015.
 * Reference: http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class History {
    private int id;
    private String location;

    public History() {
    }

    public History(String location) {
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
