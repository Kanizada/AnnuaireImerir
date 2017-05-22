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
public class Promotion implements Serializable,Parcelable {




    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String nom;
    private String annee;
    private Boolean promoActuelle;
    //
    private Integer yearStart;
    private Integer yearEnd;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(annee);
    }
    protected Promotion(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        annee = in.readString();
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel in) {
            return new Promotion(in);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };
}
