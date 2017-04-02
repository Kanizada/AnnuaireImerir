package com.imerir.annuaireimerir.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Entreprise;

/**
 * Created by student on 09/03/2017.
 */
//test de http://stackoverflow.com/questions/11151194/how-to-dynamically-add-more-fields-when-a-button-is-clicked
public class EntrepriseItemView {
    private Context context;
    private TextView nomTV,villeTV;
    private View view;
    private Entreprise entreprise;

    public EntrepriseItemView(Context context, Entreprise entreprise){
        this.context = context;
        this.entreprise = entreprise;
        init();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public TextView getNomTV() {
        return nomTV;
    }

    public void setNomTV(TextView nomTV) {
        this.nomTV = nomTV;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public TextView getVilleTV() {
        return villeTV;
    }

    public void setVilleTV(TextView villeTV) {
        this.villeTV = villeTV;
    }

    private void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        this.view = layoutInflater.inflate(R.layout.item_entreprise_in_eleve_detail,null);
        this.nomTV = (TextView) view.findViewById(R.id.enterprise_name);
        this.villeTV = (TextView) view.findViewById(R.id.enterprise_city);
        nomTV.setText(entreprise.getNom());
        villeTV.setText(entreprise.getVille());

    }
}
