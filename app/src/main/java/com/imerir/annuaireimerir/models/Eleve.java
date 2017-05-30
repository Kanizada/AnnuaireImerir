package com.imerir.annuaireimerir.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Eleve {

    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String prenom;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String siteWeb;
    private String telephoneMobile;
    private String telephoneFixe;
    private String dateInscription;
    private String email;
    private ArrayList<Entreprise> entreprises = new ArrayList<>();
    private ArrayList<Integer> entreprisesId = new ArrayList<>();
    private Promotion promotion;
    private int idpromotion;


    public Eleve(){

    }

    public Eleve(int id,String prenom, String nom, String adresse, String codePostal, String ville, String siteWeb, String telephoneMobile, String telephoneFixe, String email, int idpromotion){
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.siteWeb = siteWeb;
        this.telephoneMobile = telephoneMobile;
        this.telephoneFixe = telephoneFixe;
        this.email = email;
        this.idpromotion = idpromotion;
    }

    //

    /**
     * constructeur JSON
     * @param jsonObject
     */
    public Eleve(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.prenom = jsonObject.optString("prenom");
        this.nom = jsonObject.optString("nom");
        this.adresse = jsonObject.optString("adresse");
        this.codePostal = jsonObject.optString("code_postal");
        this.ville = jsonObject.optString("ville");
        this.siteWeb = jsonObject.optString("site_web");
        this.telephoneMobile = jsonObject.optString("telephone_mobile");
        this.telephoneFixe = jsonObject.optString("telephone_fixe");
        //this.dateInscription = jsonObject.optString("date_inscription");
        this.email = jsonObject.optString("email");
        this.idpromotion = jsonObject.optInt("idpromotion");
    }



    public int getIdpromotion() {
        return idpromotion;
    }

    public void setIdpromotion(int idpromotion) {
        this.idpromotion = idpromotion;
    }

    public ArrayList<Integer> getEntreprisesId() {
        return entreprisesId;
    }

    public void setEntreprisesId(ArrayList<Integer> entreprisesId) {
        this.entreprisesId = entreprisesId;
    }

    public void setInEntreprisesId(int entrepriseId) {
        this.entreprisesId.add(entrepriseId);
    }


    public void addEntreprise(Entreprise entreprise){this.entreprises.add(entreprise);}

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Entreprise> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(ArrayList<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }


    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public void setTelephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

}
