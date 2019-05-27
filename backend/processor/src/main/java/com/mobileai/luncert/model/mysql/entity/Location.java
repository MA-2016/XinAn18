package com.mobileai.luncert.model.mysql.entity;

public class Location {

    String country;

    String capital;

    String longitude;

    String latitude;

    public void setCountry(String country) { this.country = country; }

    public void setCapital(String capital) { this.capital = capital; }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getCountry() { return country; }

    public String getCapital() { return capital; }

    public String getLongitude() { return longitude; }

    public String getLatitude() { return latitude; }

}