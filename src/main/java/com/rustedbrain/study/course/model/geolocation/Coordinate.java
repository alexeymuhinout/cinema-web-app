package com.rustedbrain.study.course.model.geolocation;


public class Coordinate {

    private double longitude;
    private double latitude;

    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
