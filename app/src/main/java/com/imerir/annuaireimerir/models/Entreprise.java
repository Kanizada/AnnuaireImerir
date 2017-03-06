package com.imerir.annuaireimerir.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Entreprise implements Serializable{
    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String nom;
    private String adresse;
    private String code_postal;
    private String telephone;
    private String email;
    // ----------------------------------------------//
    private ActivityDomain activityDomain;
    private URL website;
    private ArrayList<Eleve> linkedEleves;
    private Double lat;
    private Double lng;
    private String city;

    public Entreprise(){

    }

    public Entreprise(JSONObject jsonObject){
        this.id = jsonObject.optInt("id");
        this.nom = jsonObject.optString("nom");
        this.adresse = jsonObject.optString("adresse");
        this.code_postal = jsonObject.optString("code_postal");
        this.telephone = jsonObject.optString("telephone");
        this.email = jsonObject.optString("email");
    }

    public Entreprise(String name, ActivityDomain activityDomain, URL website, ArrayList<Eleve> linkedEleves, String city, Double lat, Double lng) {
        this.nom = name;
        this.activityDomain = activityDomain;
        this.website = website;
        this.linkedEleves = linkedEleves;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return nom;
    }

    public void setName(String name) {
        this.nom = name;
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

    public ArrayList<Eleve> getLinkedEleves() {
        return linkedEleves;
    }

    public void setLinkedEleves(ArrayList<Eleve> linkedEleves) {
        this.linkedEleves = linkedEleves;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
