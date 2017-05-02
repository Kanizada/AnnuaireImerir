package com.imerir.annuaireimerir.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;
import com.imerir.annuaireimerir.tools.App;
import com.imerir.annuaireimerir.tools.LoginRequest;
import com.imerir.annuaireimerir.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    EditText usernameET, passwordET;
    Button loginBtn;
    TextView forgotBtn, singupBtn;
    SignInButton signinBtn;
    private static final int REQUEST_CODE = 647;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google_sign_in_only);

        //mContext = getApplicationContext();

        /*usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginBtn = (Button) findViewById(R.id.login_button);
        forgotBtn = (TextView) findViewById(R.id.password_loose);
        singupBtn = (TextView) findViewById(R.id.signup);*/
        //forgotBtn.setOnClickListener(this);
        //singupBtn.setOnClickListener(this);
        //loginBtn.setOnClickListener(this);


        signinBtn = (SignInButton) findViewById(R.id.sign_in_button);
        signinBtn.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().setHostedDomain("imerir.com").build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

    }


    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }


    private void handleResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            googleApiClient.connect();
            Intent listIntent = new Intent(LoginActivity.this,ListActivity.class);
            LoginActivity.this.startActivity(listIntent);
            finish();
        }
    }



    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {

        if (view == signinBtn){
            signIn();
        }
        /*else if (view == forgotBtn)
        {

        }
        else if (view == loginBtn) {
            Intent listIntent = new Intent(LoginActivity.this,ListActivity.class);
            LoginActivity.this.startActivity(listIntent);
            finish();
        }*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection échouée..", Toast.LENGTH_SHORT).show();
    }

}
