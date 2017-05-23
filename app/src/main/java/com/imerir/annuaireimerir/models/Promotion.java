package com.imerir.annuaireimerir.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Promotion {




    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String nom;
    private String annee;
    private Boolean promoActuelle;

    private ArrayList<Eleve> eleves = new ArrayList<>();

    //constructeur JSON
    public Promotion(JSONObject jsonObject){
        this.id =  jsonObject.optInt("id");
        this.nom = jsonObject.optString("nom");
        this.annee = jsonObject.optString("annee");
        this.promoActuelle = jsonObject.optBoolean("promo_actuelle");
    }

    public Promotion(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public ArrayList<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(ArrayList<Eleve> eleves) {
        this.eleves = eleves;
    }

    public void addEleve(Eleve eleve){ this.eleves.add(eleve);}


    public void setId(int id) {
        this.id = id;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public Boolean getPromoActuelle() {
        return promoActuelle;
    }

    public void setPromoActuelle(Boolean promoActuelle) {
        this.promoActuelle = promoActuelle;
    }

}
