package com.imerir.annuaireimerir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameET, passwordET;
    CheckBox rememberCB;
    Button loginBtn;
    TextView forgotBtn, singupBtn;
    Boolean rememberMe;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;

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

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {

        if (view == forgotBtn){

        }else if (view == singupBtn){

            /*Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
            LoginActivity.this.startActivity(registerIntent);*/

        }else if (view == loginBtn){

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(usernameET.getWindowToken(), 0);
            InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm1.hideSoftInputFromWindow(passwordET.getWindowToken(), 0);

            // get les infos dans Edit Text et conversion to string
            final String username = usernameET.getText().toString();
            final String password = passwordET.getText().toString();

            if(rememberCB.isChecked()){
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

                        if (success){
                            // Si login reussi > UserArea
                            Intent loginIntent = new Intent(LoginActivity.this,ListActivity.class);
                            LoginActivity.this.startActivity(loginIntent);
                            finish();

                        }else{
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

        }
    }
}
