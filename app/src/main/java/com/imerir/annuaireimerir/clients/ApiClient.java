package com.imerir.annuaireimerir.clients;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class ApiClient {
    private static ApiClient instance;
    Context context;
    RequestQueue queue;


    public ApiClient(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static void createInstance(Context context) {
        instance = new ApiClient(context);
    }

    public static ApiClient getInstance() {return instance;}

    public void getEntreprisesByDepartement(String numeroDepartement, String cleApi){
        String url = "http://79.137.78.233/entreprises/departement/"+numeroDepartement+"?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Entreprise> entreprises = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject entrepriseJSON = bodyArray.getJSONObject(i);
                            Entreprise entreprise = new Entreprise(entrepriseJSON);
                            entreprises.add(entreprise);
                        }
                        //LISTENER HERE
                    }else {
                        Log.e("ApiClient","getEntreprisesByDepartement() requete HTTP SUCCESS = 0");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getEntreprisesByDepartement() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getEntreprisesByDepartement() Volley error"+error);
            }
        });
        queue.add(request);
    }

    public void getEntreprisesById(String idEntreprise, String cleApi){
        String url = "http://79.137.78.233/entreprises/"+idEntreprise+"?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        Entreprise entreprise = new Entreprise(bodyArray.getJSONObject(0));
                        //LISTENER HERE
                    }else {
                        Log.e("ApiClient","getEntreprisesById() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getEnterprisesById() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getEnterprisesById() Volley error"+error);
            }
        });
        queue.add(request);
    }

    public void getEntreprises(String cleApi){
        String url = "http://79.137.78.233/entreprises/list?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Entreprise> entreprises = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject entrepriseJSON = bodyArray.getJSONObject(i);
                            Entreprise entreprise = new Entreprise(entrepriseJSON);
                            entreprises.add(entreprise);
                        }
                        //LISTENER HERE
                    }else {
                        Log.e("ApiClient","getEntreprises() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getEnterprises() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getEnterprises() Volley error"+error);
            }
        });
        queue.add(request);
    }

    public void getEleves(String cleApi){
        String url = "http://79.137.78.233/eleves/list?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Eleve> eleves = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject eleveJSON = bodyArray.getJSONObject(i);
                            Eleve eleve = new Eleve(eleveJSON);
                            eleves.add(eleve);
                        }
                        //LISTENER HERE
                    }else {
                        Log.e("ApiClient","getEleves() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getEleves() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getEleves() Volley error"+error);
            }
        });
        queue.add(request);
    }

    public void getElevesByPromotionId(String idPromotion, String cleApi){
        String url = "http://79.137.78.233/promotions/id/eleves?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Eleve> eleves = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject eleveJSON = bodyArray.getJSONObject(i);
                            Eleve eleve = new Eleve(eleveJSON);
                            eleves.add(eleve);
                        }
                        //LISTENER HERE
                    }else {
                        Log.e("ApiClient","getEleveByPromotion() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getEleveByPromotion() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getEleveByPromotion() Volley error"+error);
            }
        });
        queue.add(request);
    }

    public void getPromotions(String cleApi){
        String url = "http://79.137.78.233/promotions/list?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Promotion> promotions = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject promotionJSON = bodyArray.getJSONObject(i);
                            Promotion promotion = new Promotion(promotionJSON);
                            promotions.add(promotion);
                        }
                        //LISTENER HERE
                    }else {
                        Log.e("ApiClient","getPromotions() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getPromotions() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getPromotions() Volley error"+error);
            }
        });
        queue.add(request);
    }

}
