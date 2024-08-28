package com.gualda.sachetto.notepad.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.utils.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class UserService {

    private final Context context;
    private final UtilsService utilsService;

    public UserService(Context context) {
        this.context = context;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Criando uma instância de UtilsService usando a RequestQueue
        this.utilsService = new UtilsService(requestQueue);

    }

    public void loginUser(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = ApiConfig.BASE_URL + "/users/validate";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", user.getEmail());
            loginData.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        utilsService.makeRequest(Request.Method.POST, url, loginData, responseListener, errorListener, null);
    }

    public void logoutUser(Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        String url = ApiConfig.BASE_URL + "/users/logout";
        String token = getJwtToken();

        utilsService.makeRequest(Request.Method.GET, url, null, responseListener, errorListener, token);
   }

   public void registerUser(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
       String url = ApiConfig.BASE_URL + "/users";

       JSONObject userData = new JSONObject();
       try {
           userData.put("email", user.getEmail());
           userData.put("password", user.getPassword());
           userData.put("telephone", user.getTelephone());
           userData.put("name", user.getName());
           userData.put("birth_date", user.getBirthDate());
       } catch (JSONException e) {
           e.printStackTrace();
           Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
           return;
       }

       utilsService.makeRequest(Request.Method.POST, url, userData, responseListener, errorListener, null);
   }

   public void updatePasswordUser(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
       String url = ApiConfig.BASE_URL + "/users/update";
       String token = getJwtToken();

       JSONObject userData = new JSONObject();
       try {
           userData.put("current_password", user.getPassword());
           userData.put("new_password", user.getNewPassword());
           userData.put("new_password_confirmation", user.getNewPasswordConfirmation());
       } catch (JSONException e) {
           e.printStackTrace();
           Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
           return;
       }

       utilsService.makeRequest(Request.Method.PUT, url, userData, responseListener, errorListener, token);
   }

   public void readUser(Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
       String url = ApiConfig.BASE_URL + "/users";
       String token = getJwtToken();

       utilsService.makeRequest(Request.Method.GET, url, null, responseListener, errorListener, token);
   }

    public void updateData(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = ApiConfig.BASE_URL + "/users/update";
        String token = getJwtToken();

        JSONObject userData = new JSONObject();
        try {
            userData.put("name", user.getName());
            userData.put("telephone", user.getTelephone());
            userData.put("email", user.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        utilsService.makeRequest(Request.Method.PUT, url, userData, responseListener, errorListener, token);
    }

    private String getJwtToken() {
        JWT jwt = new JWT();
        return jwt.getJwtToken(context);
    }
}
