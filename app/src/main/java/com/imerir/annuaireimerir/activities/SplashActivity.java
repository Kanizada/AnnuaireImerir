package com.imerir.annuaireimerir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.imerir.annuaireimerir.clients.ApiClient;

/**
 * Created by student on 10/01/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CHARGEMENT DES DONNES DEPUIS LA BDD VIA LAPI
        //A VOIR SI IL FAUT LE METTRE DANS UN THREAD POUR DES RAISONS DE PERFORMANCES
        ApiClient.createInstance(this);
        ApiClient.getInstance().getEleves("devTmpKey");
        ApiClient.getInstance().getEntreprises("devTmpKey");
        ApiClient.getInstance().getPromotions("devTmpKey");
        //
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
