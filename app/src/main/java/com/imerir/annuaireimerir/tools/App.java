package com.imerir.annuaireimerir.tools;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.imerir.annuaireimerir.clients.ApiClient;


/**
 * Created by student on 10/01/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.createInstance(this);
    }

}
