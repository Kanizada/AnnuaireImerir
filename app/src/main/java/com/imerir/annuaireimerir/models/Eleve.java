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
public class Eleve implements Serializable ,Parcelable{

    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String prenom;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String siteWeb;
    private String telephoneMobile;
    //private String telephonePerso;
    private String telephoneFixe;
    private String dateInscription;
    private String email;
    private ArrayList<Entreprise> entreprises = new ArrayList<>();
    private Promotion promotion;
    //


    //constructor from JSON
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
        this.dateInscription = jsonObject.optString("date_inscription");
        this.email = jsonObject.optString("email");
        this.promotion = new Promotion(jsonObject.optJSONObject("promotion"));
        //A VOIR POUR LA LISTE DENTREPRISE
        JSONArray liste_entreprise = jsonObject.optJSONArray("entreprises");
        if (liste_entreprise != null && liste_entreprise.length() > 0) {
            for (int i = 0; i < liste_entreprise.length(); i++) {
                JSONObject entrepriseJSON = liste_entreprise.optJSONObject(i);
                Entreprise entreprise = new Entreprise(entrepriseJSON);
                //entreprise.setEleve(this);
                entreprises.add(entreprise);
            }
        }
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





    //
    public void lierEntreprises(){
        ArrayList<Entreprise> tmp_entreprises = this.getEntreprises();
        for (Entreprise entreprise:tmp_entreprises) {
            entreprise.addEleve(this);
        }
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

    public static final Creator<Eleve> CREATOR = new Creator<Eleve>() {
        @Override
        public Eleve createFromParcel(Parcel in) {
            return new Eleve(in);
        }

        @Override
        public Eleve[] newArray(int size) {
            return new Eleve[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(prenom);
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeString(codePostal);
        dest.writeString(ville);
        dest.writeString(siteWeb);
        dest.writeString(telephoneMobile);
        dest.writeString(telephoneFixe);
        dest.writeString(dateInscription);
        dest.writeString(email);
        dest.writeTypedList(entreprises);
        dest.writeParcelable(promotion, flags);
        //dest.writeParcelable(entreprise, flags);
    }
    protected Eleve(Parcel in) {
        id = in.readInt();
        prenom = in.readString();
        nom = in.readString();
        adresse = in.readString();
        codePostal = in.readString();
        siteWeb = in.readString();
        telephoneMobile = in.readString();
        telephoneFixe = in.readString();
        dateInscription = in.readString();
        email = in.readString();
        entreprises = in.createTypedArrayList(Entreprise.CREATOR);
        promotion = in.readParcelable(Promotion.class.getClassLoader());
        //entreprise = in.readParcelable(Entreprise.class.getClassLoader());
    }
}
