package com.example.summervacationhomework.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sight {
    @Id
    private String id;
    private String sightName;
    private String zone;
    private String category;
    private String photoURL;
    private String description;
    private String address;

    public String getSightName() {
        return sightName;
    }

    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SightName: "+sightName+"\n"
                +"Zone: "+zone+"\n"
                +"Category: "+ category+"\n"
                +"PhotoURL: "+photoURL+"\n"
                +"Description: "+description+"\n"
                +"Address: "+address+"\n\n";
    }
}
