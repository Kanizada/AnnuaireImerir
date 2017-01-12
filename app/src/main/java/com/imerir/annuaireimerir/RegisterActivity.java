package com.imerir.annuaireimerir;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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
        setContentView(R.layout.activity_register);
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
            String prenom = ;
            String nom = nomET.getText().toString();
            String nom = nomET.getText().toString();
            String nom = nomET.getText().toString();
        }
    }
}
