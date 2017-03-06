package com.imerir.annuaireimerir.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Promotion implements Serializable {




    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String nom;
    private String annee;
    private Boolean promoActuelle;
    //

    //constructeur JSON
    public Promotion(JSONObject jsonObject){
        this.id =  jsonObject.optInt("id");
        this.nom = jsonObject.optString("nom");
        this.annee = jsonObject.optString("annee");
        this.promoActuelle = jsonObject.optBoolean("promo_actuelle");
    }




    private Integer yearStart;
    private Integer yearEnd;
    private ArrayList<Eleve> eleves;
    private Formation formation;

    public Promotion(){

    }



    public Promotion(Integer id, String nom, Integer yearStart, Integer yearEnd, ArrayList<Eleve> eleves, Formation formation) {
        this.id = id;
        this.nom = nom;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.eleves = eleves;
        this.formation = formation;
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

    public Integer getYearStart() {
        return yearStart;
    }

    public void setYearStart(Integer yearStart) {
        this.yearStart = yearStart;
    }

    public Integer getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(Integer yearEnd) {
        this.yearEnd = yearEnd;
    }

    public ArrayList<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(ArrayList<Eleve> eleves) {
        this.eleves = eleves;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

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
