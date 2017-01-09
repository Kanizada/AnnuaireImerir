package com.imerir.annuaireimerir;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Enterprise {

    private String name;
    private ActivityDomain activityDomain;
    private URL website;
    private ArrayList<Student> linkedStudents;
    private Double lat;
    private Double lng;

    public Enterprise(String name, ActivityDomain activityDomain, URL website, ArrayList<Student> linkedStudents, Double lat, Double lng) {
        this.name = name;
        this.activityDomain = activityDomain;
        this.website = website;
        this.linkedStudents = linkedStudents;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityDomain getActivityDomain() {
        return activityDomain;
    }

    public void setActivityDomain(ActivityDomain activityDomain) {
        this.activityDomain = activityDomain;
    }

    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public ArrayList<Student> getLinkedStudents() {
        return linkedStudents;
    }

    public void setLinkedStudents(ArrayList<Student> linkedStudents) {
        this.linkedStudents = linkedStudents;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
