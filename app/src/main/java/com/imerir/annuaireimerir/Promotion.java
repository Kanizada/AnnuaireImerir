package com.imerir.annuaireimerir;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Promotion {


    // ATTRIBUTS ACTUELLEMENTS DISPONIBLES VIA L'API //
    private int id;
    private String name;
    private String annee;
    private Boolean promoActuelle;
    //

    //constructeur JSON
    public Promotion(JSONObject jsonObject){
        this.id =  jsonObject.optInt("id");
        this.name = jsonObject.optString("nom");
        this.annee = jsonObject.optString("annee");
        this.promoActuelle = jsonObject.optBoolean("promo_actuelle");
    }




    private Integer yearStart;
    private Integer yearEnd;
    private ArrayList<Student> students;
    private Formation formation;

    public Promotion(){

    }



    public Promotion(Integer id, String name, Integer yearStart, Integer yearEnd, ArrayList<Student> students, Formation formation) {
        this.id = id;
        this.name = name;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.students = students;
        this.formation = formation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
