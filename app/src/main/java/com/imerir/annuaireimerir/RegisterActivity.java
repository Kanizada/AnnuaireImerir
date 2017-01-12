package com.imerir.annuaireimerir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by student on 12/01/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nomET,prenomET,usernameET,passwordET,passwordconfirmET, mailET;
    Button registerBtn;
    EmailValidator emailValidator = new EmailValidator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.id.activity_register);
        nomET = (EditText) findViewById(R.id.nomET);
        prenomET = (EditText) findViewById(R.id.prenomET);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        passwordconfirmET = (EditText) findViewById(R.id.passwordconfirmET);
        mailET = (EditText) findViewById(R.id.mailET);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == registerBtn){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(nomET.getWindowToken(), 0);
            InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm1.hideSoftInputFromWindow(prenomET.getWindowToken(), 0);
            InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm2.hideSoftInputFromWindow(usernameET.getWindowToken(), 0);
            InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm3.hideSoftInputFromWindow(passwordET.getWindowToken(), 0);
            InputMethodManager imm4 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm4.hideSoftInputFromWindow(passwordconfirmET.getWindowToken(), 0);
            InputMethodManager imm5 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm5.hideSoftInputFromWindow(mailET.getWindowToken(), 0);

            String nom = nomET.getText().toString();
            String prenom = prenomET.getText().toString();
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            String passwordConfirm = passwordconfirmET.getText().toString();
            String mail = mailET.getText().toString();

            if (password.equals(passwordConfirm) && !nom.equals(prenom) && !nom.equals(username) && !password.equals(prenom) && !password.equals(nom) && !password.equals(username) && emailValidator.validate(mail)){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // création de la récéption du retour de la requéte db
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            // si la $response["success"] du script php return true
                            if (success) {
                                String message = getResources().getString(R.string.msg_email_validation);
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(message);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        RegisterActivity.this.startActivity(registerIntent);
                                    }
                                });
                                builder.create();
                                builder.show();

                            }else{
                                // erreur de communication avec la db ou username deja pris
                                String message = getResources().getString(R.string.messageUsernamenotav);
                                String messageTry = getResources().getString(R.string.messageTry);
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(message)
                                        .setNegativeButton(messageTry, null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // queue de la requete d'enregistrement
                RegisterRequest registerRequest = new RegisterRequest(nom, prenom, username, password, mail,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        }
    }
}
