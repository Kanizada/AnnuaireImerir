package com.imerir.annuaireimerir;

import android.app.Application;


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
