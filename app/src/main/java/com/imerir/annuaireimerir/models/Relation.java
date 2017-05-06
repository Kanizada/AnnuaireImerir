package com.imerir.annuaireimerir.models;

import org.json.JSONObject;

/**
 * Created by student on 04/05/2017.
 */

public class Relation {

    private int id;
    private int ideleve;
    private int identreprise;

    public Relation(int ideleve, int identreprise){
        this.ideleve = ideleve;
        this.identreprise = identreprise;
    }

    public Relation(JSONObject jsonObject){
        this.id = jsonObject.optInt("id");
        this.ideleve = jsonObject.optInt("ideleve");
        this.identreprise = jsonObject.optInt("identreprise");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentreprise() {
        return identreprise;
    }

    public void setIdentreprise(int identreprise) {
        this.identreprise = identreprise;
    }

    public int getIdeleve() {
        return ideleve;
    }

    public void setIdeleve(int ideleve) {
        this.ideleve = ideleve;
    }
}
