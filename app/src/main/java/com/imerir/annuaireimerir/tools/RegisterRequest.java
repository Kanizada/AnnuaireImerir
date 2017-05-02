package com.imerir.annuaireimerir.tools;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class RegisterRequest extends StringRequest{

    // path script php pour la requete d'enregistrement
    private static final String REGISTER_RESQUEST_URL = "";
    // création du map qui va contenir les infos à injecter dans la db
    private Map<String, String> params;

    //exemple des données à envoyer au script pour les enregistrer sur la bdd
    public RegisterRequest(String nom, String prenom, String username, String password, String mail, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_RESQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nom", nom);
        params.put("prenom", prenom);
        params.put("username", username);
        params.put("password", password);
        params.put("mail", mail);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
