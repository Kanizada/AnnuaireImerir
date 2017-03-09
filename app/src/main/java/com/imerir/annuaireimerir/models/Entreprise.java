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
public class Entreprise implements Serializable, Parcelable{
    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String nom;
    private String adresse;
    private String code_postal;
    private String telephone;
    private String email;
    private String siteWeb;
    private String ville;
    // ----------------------------------------------//
    private DomaineActivite domaineActivite;
    private ArrayList<Eleve> eleves = new ArrayList<>();
    private Double lat;
    private Double lng;


    public Entreprise(){

    }

    public Entreprise(JSONObject jsonObject){
        this.id = jsonObject.optInt("id");
        this.nom = jsonObject.optString("nom");
        this.adresse = jsonObject.optString("adresse");
        this.code_postal = jsonObject.optString("code_postal");
        this.ville = jsonObject.optString("ville");
        this.telephone = jsonObject.optString("telephone");
        this.email = jsonObject.optString("email");
        this.siteWeb = jsonObject.optString("site_web");
    }

    public void addEleve(Eleve eleve){
        eleves.add(eleve);
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String city) {
        this.ville = city;
    }


    public DomaineActivite getDomaineActivite() {
        return domaineActivite;
    }

    public void setDomaineActivite(DomaineActivite domaineActivite) {
        this.domaineActivite = domaineActivite;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String website) {
        this.siteWeb = website;
    }

    public ArrayList<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(ArrayList<Eleve> eleves) {
        this.eleves = eleves;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeString(code_postal);
        dest.writeString(ville);
        dest.writeString(telephone);
        dest.writeString(email);
        dest.writeString(siteWeb);
    }
    protected Entreprise(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        adresse = in.readString();
        code_postal = in.readString();
        telephone = in.readString();
        email = in.readString();
        ville = in.readString();
        siteWeb = in.readString();
    }

    public static final Creator<Entreprise> CREATOR = new Creator<Entreprise>() {
        @Override
        public Entreprise createFromParcel(Parcel in) {
            return new Entreprise(in);
        }

        @Override
        public Entreprise[] newArray(int size) {
            return new Entreprise[size];
        }
    };
}
