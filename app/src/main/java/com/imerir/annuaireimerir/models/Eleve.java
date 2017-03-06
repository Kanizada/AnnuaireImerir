package com.imerir.annuaireimerir.models;

import org.json.JSONObject;

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
    private String siteWeb;
    private String telephoneMobile;
    private String telephonePerso;
    private String dateInscription;
    private String email;
    private ArrayList<Entreprise> entreprises = new ArrayList<>();
    private Promotion promotion;
    //


    //constructor from JSON
    public Eleve(JSONObject jsonObject){
        this.id = jsonObject.optInt("id");
        this.prenom = jsonObject.optString("prenom");
        this.nom = jsonObject.optString("nom");
        this.adresse = jsonObject.optString("adresse");
        this.siteWeb = jsonObject.optString("site_web");
        this.telephoneMobile = jsonObject.optString("telephone_mobile");
        this.telephonePerso = jsonObject.optString("telephone_perso");
        this.dateInscription = jsonObject.optString("date_inscription");
        this.email = jsonObject.optString("email");
        //A VOIR POUR LA LISTE DENTREPRISE
        this.promotion = new Promotion(jsonObject.optJSONObject("promotion"));

    }




    private Formation formation;
    private Entreprise entreprise;


    public Eleve(){

    }


    //premier jet constructor
    public Eleve(String prenom, String nom, String adresse, Entreprise entreprise, Formation formation, Promotion promotion) {
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.entreprise = entreprise;
        this.formation = formation;
        this.promotion = promotion;
    }

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

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
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

    public String getTelephonePerso() {
        return telephonePerso;
    }

    public void setTelephonePerso(String telephonePerso) {
        this.telephonePerso = telephonePerso;
    }

}
