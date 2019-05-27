package com.mobileai.luncert.model.mysql.entity;

import com.mobileai.luncert.utils.Convert;

import scala.collection.mutable.StringBuilder;

public class Position {

    float latitude;

    float longitude;

    /**
     * like: China
     * or: 2
     */
    String countryId;

    String provinceId;

    public Position(float latitude, float longitude, String countryId, String provinceId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryId = countryId;
        this.provinceId = provinceId;
    }
    
    public void setLatitude(float latitude) { this.latitude = latitude; }

    public void setLongitude(float longitude) { this.longitude = longitude; }

    public void setCountryId(String countryId) { this.countryId = countryId; }

    public void setProvinceId(String provinceId) { this.provinceId = provinceId; }

    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude; }

    public String getCountryId() { return countryId; }

    public String getProvinceId() { return provinceId; }

    public String toJSONString() {
        return new StringBuilder().append("{")
                .append("\"latitude\":").append(Convert.doubleCastString(latitude))
                .append(",\"longitude\":").append(Convert.doubleCastString(longitude))
                .append(",\"countryId\":\"").append(countryId).append("\"")
                .append(",\"provinceId\":\"").append(provinceId).append("\"")
                .append("}").toString();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nlatitude: ").append(latitude)
                .append("\nlongitude: ").append(longitude)
                .append("\ncountry: ").append(countryId)
                .append("\nprovince: ").append(provinceId).toString();
    }

}