package com.imerir.annuaireimerir.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.imerir.annuaireimerir.R;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    SignInButton signinBtn;
    private static final int REQUEST_CODE = 647;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signinBtn = (SignInButton) findViewById(R.id.sign_in_button);
        signinBtn.setOnClickListener(this);
        //instanciation google connect api
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().setHostedDomain("imerir.com").build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

    }


    //call activity for result de l'api google
    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQUEST_CODE);
    }

    //Si l'activity for result retourne le bon code on gére la connexion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }


    //Si le result est un succès on se connecte sur l'api google et on change d'activité vers la listactivity
    private void handleResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            googleApiClient.connect();
            Intent listIntent = new Intent(LoginActivity.this,ListActivity.class);
            LoginActivity.this.startActivity(listIntent);
            finish();
        }else {
            Toast.makeText(this,"Erreur de connexion google",Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connexion échouée..", Toast.LENGTH_SHORT).show();
    }

}
