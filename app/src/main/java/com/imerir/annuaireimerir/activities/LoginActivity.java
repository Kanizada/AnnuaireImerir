package com.imerir.annuaireimerir.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;
import com.imerir.annuaireimerir.tools.LoginRequest;
import com.imerir.annuaireimerir.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Eleve> liste_eleves = new ArrayList<>();
    ArrayList<Entreprise> liste_entreprises = new ArrayList<>();
    ArrayList<Promotion> liste_promotions = new ArrayList<>();
    EditText usernameET, passwordET;
    CheckBox rememberCB;
    Button loginBtn;
    TextView forgotBtn, singupBtn;
    Boolean rememberMe;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;

    Boolean local = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        rememberCB = (CheckBox) findViewById(R.id.remind_checkbox);
        loginBtn = (Button) findViewById(R.id.login_button);
        forgotBtn = (TextView) findViewById(R.id.password_loose);
        singupBtn = (TextView) findViewById(R.id.signup);

        forgotBtn.setOnClickListener(this);
        singupBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefEditor = loginPreferences.edit();
        rememberMe = loginPreferences.getBoolean("rememberMe", false);

        if(rememberMe){
            usernameET.setText(loginPreferences.getString("username", ""));
            passwordET.setText(loginPreferences.getString("password", ""));
            rememberCB.setChecked(true);
        }

    }

    //test au cas ou on load au splashscreen
    /*public void passData(Intent intent){
        liste_eleves = getIntent().getParcelableArrayListExtra("liste_eleves");
        liste_entreprises = getIntent().getParcelableArrayListExtra("liste_entreprises");
        liste_promotions = getIntent().getParcelableArrayListExtra("liste_promotions");
        intent.putParcelableArrayListExtra("liste_eleves", liste_eleves);
        intent.putParcelableArrayListExtra("liste_entreprises", liste_entreprises);
        intent.putParcelableArrayListExtra("liste_promotions", liste_promotions);
        for (Eleve eleve :liste_eleves) {
            Log.e("LA onElevesReceived",eleve.getNom() + " " +eleve.getPrenom());
        }
        for (Entreprise entreprise :liste_entreprises) {
            Log.e("LA onEntrepriReceived",entreprise.getNom() + " " +entreprise.getNom());
        }
        for (Promotion promotion :liste_promotions) {
            Log.e("LA onPromotionReceived",promotion.getNom() + " " +promotion.getAnnee());
        }

    }*/

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {

        if (view == forgotBtn){

        }else if (view == singupBtn){

            //Intent registerIntent = new Intent(LoginActivity.this,ListActivity.class);
            //LoginActivity.this.startActivity(registerIntent);

        }else if (view == loginBtn){

            if(!local) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(usernameET.getWindowToken(), 0);
                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(passwordET.getWindowToken(), 0);

                // get les infos dans Edit Text et conversion to string
                final String username = usernameET.getText().toString();
                final String password = passwordET.getText().toString();

                

                if (rememberCB.isChecked()) {
                    loginPrefEditor.putBoolean("rememberMe", true);
                    loginPrefEditor.putString("username", username);
                    loginPrefEditor.putString("password", password);
                    loginPrefEditor.apply();
                } else {
                    loginPrefEditor.clear();
                    loginPrefEditor.apply();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // compare la reponse de la DB en JSON
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                // Si login reussi > ListActivity
                                Intent loginIntent = new Intent(LoginActivity.this, ListActivity.class);
                                LoginActivity.this.startActivity(loginIntent);
                                finish();

                            } else {
                                // message d'erreur en cas de réponse login negative
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Nom d'utilisateur ou mot de passe incorrect")
                                        .setNegativeButton("Ressayer", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            } else {
                Intent listIntent = new Intent(LoginActivity.this,ListActivity.class);
                //passData(listIntent);
                LoginActivity.this.startActivity(listIntent);
            }
        }
    }
}
