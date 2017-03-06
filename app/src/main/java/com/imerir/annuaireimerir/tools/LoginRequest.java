package com.imerir.annuaireimerir.tools;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Axel Zapata on 10/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class LoginRequest extends StringRequest {

    // path jusqu'au script de requete login
    private static final String LOGIN_RESQUEST_URL = "http://imerir.com/webservice/login.php";
    
    // création du map qui va contenir le username et le mdp
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_RESQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
