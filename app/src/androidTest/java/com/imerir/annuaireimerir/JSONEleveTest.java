package com.imerir.annuaireimerir;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.imerir.annuaireimerir.models.Eleve;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;


/**
 * Created by student on 30/05/2017.
 */

public class JSONEleveTest {

    //Test du chargement du fichier eleve.json
    @Test
    public void testChargementElevejson() throws IOException {
        assertTrue(loadJSONFromAsset("eleve.json")!=null);
    }

    //Test de construction d'un élève à partir du fichier eleve.json, l'élève est censé avoir un id avec une valeur de 4
    @Test
    public void eleveJSON_working() throws Exception{
        //création du JSONObject avec le retour de la fonction loadJSONFromAsset() pour le fichier eleve.json
        JSONObject json = new JSONObject(loadJSONFromAsset("eleve.json"));
        //recupération de l'array body
        JSONArray bodyArray = json.getJSONArray("body");
        //récupération du premier objet dans l'array body
        JSONObject eleveJSON = bodyArray.getJSONObject(0);

        assertTrue(new Eleve(eleveJSON).getId() == 4);
    }

    //Test de construction d'un élève à partir du fichier elevenull.json, l'élève est censé avoir un id avec une valeur de 0 car le champ id n'est pas renseigné
    @Test
    public void eleveJSON_notworking() throws Exception{
        //création du JSONObject avec le retour de la fonction loadJSONFromAsset() pour le fichier elevenull.json
        JSONObject json = new JSONObject(loadJSONFromAsset("elevenull.json"));
        //recupération de l'array body
        JSONArray bodyArray = json.getJSONArray("body");
        //récupération du premier objet dans l'array body
        JSONObject eleveJSON = bodyArray.getJSONObject(0);

        assertTrue(new Eleve(eleveJSON).getId() == 0);
    }

    //Fonction de chargement du fichier json
    public String loadJSONFromAsset(String file) {
        String json = null;
        try {

            InputStream is = InstrumentationRegistry.getTargetContext().getAssets().open(file);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
