package com.imerir.annuaireimerir;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by student on 10/01/2017.
 */

public class ApiClient {
    private static ApiClient instance;
    Context context;
    RequestQueue queue;
    int success;


    public ApiClient(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static void createInstance(Context context) {
        instance = new ApiClient(context);
    }

    public static ApiClient getInstance() {return instance;}


}
