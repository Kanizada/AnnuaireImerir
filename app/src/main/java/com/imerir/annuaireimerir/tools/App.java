package com.imerir.annuaireimerir.tools;

import android.app.Application;

import com.imerir.annuaireimerir.clients.ApiClient;


/**
 * Created by student on 10/01/2017.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.createInstance(this);
    }
}
