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
import com.imerir.annuaireimerir.models.Relation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class ApiClient {
    private static ApiClient instance;
    private String URLHeader = "http://79.137.78.233/api/";
    private String cleApi = "BcngKw4Qg1TOxUAVOs2Hu5LtemrakphVBEclagcri6fQ0PLPhNwLSif7FGhZ5zVHe73pofQGl5wlVhbd5IZY1e5GUnFZXAGbIJA6YqNkwjGrBREHADGBRLhM9XL6yshhcSnajavPubg63VNDyteqfw";
    Context context;
    RequestQueue queue;
    boolean entreprisesLoaded;
    boolean elevesLoaded;
    boolean promotionsLoaded;


    public ApiClient(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static void createInstance(Context context) {
        instance = new ApiClient(context);
    }

    public static ApiClient getInstance() {return instance;}

    public void cancelQueue(){
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public void loadData(final  OnElevesListener listener, final OnEntreprisesListener listener2, final OnPromotionsListener listener3, final OnRelationsListener listener4){
        getEleves(cleApi,listener);
        getEntreprises(cleApi,listener2);
        getPromotions(cleApi,listener3);
        getRelations(cleApi,listener4);
    }

    //requete api pour obtenir les relations eleves-entreprises
    public void getRelations(String cleApi, final OnRelationsListener listener){
        String url = URLHeader+"relations/list/"+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Relation> relations = new ArrayList<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject relationJSON = bodyArray.getJSONObject(i);
                            Relation relation = new Relation(relationJSON);
                            relations.add(relation);
                        }
                        listener.onRelationsReceived(relations);
                    }else {
                        Log.e("ApiClient","getRelations() requete HTTP SUCCESS = 0");
                        listener.onRelationsFailed("getRelations() requete HTTP SUCCESS = 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ApiClient", "getRelations() JSON error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiClient","getRelations() Volley error"+error);
                listener.onRelationsFailed(error.toString());
            }
        });
        queue.add(request);
    }

    //requete api pour obtenir les entreprises
    public void getEntreprises(String cleApi, final OnEntreprisesListener listener){
        String url = URLHeader+"entreprises/list/"+cleApi;
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
                        listener.onEntreprisesReceived(entreprises,entreprisesIdObj);
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

    //requete api pour obtenir les eleves
    public void getEleves(String cleApi, final OnElevesListener listener){
        String url = URLHeader+"eleves/list/"+cleApi;
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
                        listener.onElevesReceived(eleves,elevesIdObj);

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

    //requete api pour obtenir les promotions
    public void getPromotions(String cleApi,final OnPromotionsListener listener){
        String url = URLHeader+"promotions/list/"+cleApi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject retourJSON = new JSONObject(response);
                    int success = retourJSON.getInt("success");
                    if (success == 1){
                        JSONArray bodyArray = retourJSON.getJSONArray("body");
                        ArrayList<Promotion> promotions = new ArrayList<>();
                        SparseArray<Promotion> promotionsById = new SparseArray<>();
                        for (int i = 0; i<bodyArray.length();i++){
                            JSONObject promotionJSON = bodyArray.getJSONObject(i);
                            Promotion promotion = new Promotion(promotionJSON);
                            promotions.add(promotion);
                            promotionsById.append(promotion.getId(),promotion);
                        }
                        //LISTENER HERE
                        listener.onPromotionsReceived(promotions,promotionsById);
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


    public interface OnElevesListener {
        void onElevesReceived(ArrayList<Eleve> eleves, SparseArray<Eleve> elevesIdObj);
        void onElevesFailed(String error);
    }

    public interface OnRelationsListener{
        void onRelationsReceived(ArrayList<Relation> relations);
        void onRelationsFailed(String error);
    }

    public interface OnEntreprisesListener {
        void onEntreprisesReceived(ArrayList<Entreprise> entreprises, SparseArray<Entreprise> entrepriseIdObj);
        void onEntreprisesFailed(String error);

    }

    public interface OnPromotionsListener {
        void onPromotionsReceived(ArrayList<Promotion> promotions, SparseArray<Promotion> promotionsById);
        void onPromotionsFailed(String error);
    }


}
