package com.imerir.annuaireimerir.clients;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

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
import java.util.HashMap;

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

    public void getEntreprises(String cleApi, final OnEntreprisesListener listener){
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
                        SparseArray<Entreprise> entreprisesIdObj = new SparseArray<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject entrepriseJSON = bodyArray.getJSONObject(i);
                            Entreprise entreprise = new Entreprise(entrepriseJSON);
                            entreprises.add(entreprise);
                            entreprisesIdObj.append(entreprise.getId(),entreprise);
                        }
                        //LISTENER HERE
                        //listener.onEntreprisesReceived(entreprises);
                        listener.onEntreprisesReceivedSparse(entreprises,entreprisesIdObj);
                    }else {
                        Log.e("ApiClient","getEntreprises() requete HTTP SUCCESS = 0");
                        listener.onEntreprisesFailed("getEntreprises() requete HTTP SUCCESS = 0");
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
                listener.onEntreprisesFailed(error.toString());
            }
        });
        queue.add(request);
    }

    public void getEleves(String cleApi, final OnElevesListener listener){
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
                        SparseArray<Eleve> elevesIdObj = new SparseArray<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject eleveJSON = bodyArray.getJSONObject(i);
                            Eleve eleve = new Eleve(eleveJSON);
                            eleves.add(eleve);
                            elevesIdObj.append(eleve.getId(),eleve);
                        }
                        //LISTENER HERE
                        //listener.onElevesReceived(eleves);
                        listener.onElevesReceivedSparse(eleves,elevesIdObj);
                    }else {
                        Log.e("ApiClient","getEleves() requete HTTP SUCCESS = 0");
                        listener.onElevesFailed("getEleves() requete HTTP SUCCESS = 0");
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
                listener.onElevesFailed(error.toString());
            }
        });
        queue.add(request);
    }

    public void getPromotions(String cleApi,final OnPromotionsListener listener){
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
                        listener.onPromotionsReceived(promotions);
                    }else {
                        Log.e("ApiClient","getPromotions() requete HTTP SUCCESS = 0");
                        listener.onPromotionsFailed("getPromotions() requete HTTP SUCCESS = 0");
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
                listener.onPromotionsFailed(error.toString());
            }
        });
        queue.add(request);
    }


    /*public void getElevesByPromotionId(String idPromotion, String cleApi,final OnElevesListener listener){
        String url = "http://79.137.78.233/promotions/id/eleves?key="+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        //AJOUTER UNE VERIFICATION DU CONTENU DE BODY
                        //if ((bodyArray.isNull(0)){}
                        ArrayList<Eleve> eleves = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject eleveJSON = bodyArray.getJSONObject(i);
                            Eleve eleve = new Eleve(eleveJSON);
                            eleves.add(eleve);
                        }
                        //LISTENER HERE
                        listener.onElevesReceived(eleves);
                    }else {
                        Log.e("ApiClient","getEleveByPromotion() requete HTTP SUCCESS = 0");
                        listener.onElevesFailed("getEleveByPromotion() requete HTTP SUCCESS = 0");
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
                listener.onElevesFailed(error.toString());
            }
        });
        queue.add(request);
    }*/

    /*public void getEntreprisesByDepartement(String numeroDepartement, String cleApi, final OnEntreprisesListener listener){
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
                        listener.onEntreprisesReceived(entreprises);
                    }else {
                        Log.e("ApiClient","getEntreprisesByDepartement() requete HTTP SUCCESS = 0");
                        listener.onEntrepriseFailed("getEntreprisesByDepartement() requete HTTP SUCCESS = 0");
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
                listener.onEntrepriseFailed(error.toString());
            }
        });
        queue.add(request);
    }*/

    /*public void getEntrepriseById(String idEntreprise, String cleApi, final OnEntreprisesListener listener){
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
                        listener.onEntrepriseReceived(entreprise);
                    }else {
                        Log.e("ApiClient","getEntrepriseById() requete HTTP SUCCESS = 0");
                        listener.onEntrepriseFailed("getEntrepriseById() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getEnterpriseById() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getEnterpriseById() Volley error"+error);
                listener.onEntrepriseFailed(error.toString());
            }
        });
        queue.add(request);
    }*/



    public interface OnElevesListener {
        void onElevesReceivedSparse(ArrayList<Eleve> eleves, SparseArray<Eleve> elevesIdObj);
        void onElevesFailed(String error);
    }

    public interface OnEntreprisesListener {
        void onEntreprisesReceivedSparse(ArrayList<Entreprise> entreprises, SparseArray<Entreprise> entrepriseIdObj);
        void onEntreprisesFailed(String error);

    }

    public interface OnPromotionsListener {
        void onPromotionsReceived(ArrayList<Promotion> promotions);
        void onPromotionsFailed(String error);
    }

}
