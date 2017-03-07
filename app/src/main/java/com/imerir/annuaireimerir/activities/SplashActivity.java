package com.imerir.annuaireimerir.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class SplashActivity extends AppCompatActivity implements ApiClient.OnEntreprisesListener,ApiClient.OnPromotionsListener,ApiClient.OnElevesListener{

    //test de chargements des data sur le splashscreen
    ArrayList<Eleve> liste_eleves = new ArrayList<>();
    ArrayList<Entreprise> liste_entreprises = new ArrayList<>();
    ArrayList<Promotion> liste_promotions = new ArrayList<>();
    Intent intent;
    Thread thread;
    SplashActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.createInstance(this);
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        //test au cas ou on load au splashscreen
        /*dataLoading();
        if (!thread.isAlive()){
            startActivity(intent);
            finish();
        }*/
    }

    //test au cas ou on load au splashscreen
    /*public void dataLoading(){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ApiClient.getInstance().getEleves("devTmpKey",context);
                    ApiClient.getInstance().getEntreprises("devTmpKey",context);
                    ApiClient.getInstance().getPromotions("devTmpKey",context);
                }
            });
            thread.run();
    }*/

    @Override
    public void onElevesReceived(ArrayList<Eleve> eleves) {
        liste_eleves = eleves;
        for (Eleve eleve :eleves) {
            Log.e("SC onElevesReceived",eleve.getNom() + " " +eleve.getPrenom());
        }
        intent.putParcelableArrayListExtra("liste_eleves",liste_eleves);
        Toast.makeText(this,"Succès du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onElevesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntreprisesReceived(ArrayList<Entreprise> entreprises) {
        liste_entreprises = entreprises;
        for (Entreprise entreprise :entreprises) {
            Log.e("SC onElevesReceived",entreprise.getNom() + " " +entreprise.getNom());
        }
        intent.putParcelableArrayListExtra("liste_entreprises",liste_entreprises);

        Toast.makeText(this,"Succès du chargement de la liste des entreprises",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntreprisesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des entreprises",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntrepriseReceived(Entreprise entreprise) {

    }

    @Override
    public void onEntrepriseFailed(String error) {

    }

    @Override
    public void onPromotionsReceived(ArrayList<Promotion> promotions) {
        liste_promotions = promotions;
        for (Promotion promotion :promotions) {
            Log.e("SC onElevesReceived",promotion.getNom() + " " +promotion.getAnnee());
        }
        intent.putParcelableArrayListExtra("liste_promotions",liste_promotions);
        Toast.makeText(this,"Succès du chargement de la liste des promotions",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPromotionsFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
    }
}
