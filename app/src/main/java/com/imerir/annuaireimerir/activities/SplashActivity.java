package com.imerir.annuaireimerir.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class SplashActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.createInstance(this);
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


}
