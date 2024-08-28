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
    private Context context;
    User user = new User();

    public UserService(Context context) {
        this.context = context;
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                loginData,
                responseListener,
                errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void logoutUser(User user,Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        String url = ApiConfig.BASE_URL + "/users/logout";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                responseListener,
                errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = user.getToken();
                if (token == null) {
                    Log.e("Headers", "Token de autenticação não encontrado"+token);
                }
                Map<String, String> headers = ApiConfig.configHeaders(token);
                Log.d("Headers", headers.toString());
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
   }

   public void registerUser(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
       String url = ApiConfig.BASE_URL + "/users";

       JSONObject UserData = new JSONObject();
       try {
           UserData.put("email", user.getEmail());
           UserData.put("password", user.getPassword());
           UserData.put("telephone", user.getTelephone());
           UserData.put("name", user.getName());
           UserData.put("birth_date", user.getBirthDate());
       } catch (JSONException e) {
           e.printStackTrace();
           Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
           return;
       }

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
               Request.Method.POST,
               url,
               UserData,
               responseListener,
               errorListener);

       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(jsonObjectRequest);
   }

   public void updatePasswordUser(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
       String url = ApiConfig.BASE_URL + "/users/update";

       JSONObject UserData = new JSONObject();
       try {
           UserData.put("current_password", user.getPassword());
           UserData.put("new_password", user.getNewPassword());
           UserData.put("new_password_confirmation", user.getNewPasswordConfirmation());
       } catch (JSONException e) {
           e.printStackTrace();
           Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
           return;
       }

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
               Request.Method.PUT,
               url,
               UserData,
               responseListener,
               errorListener){
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               String token = user.getToken();
               if (token == null) {
                   Log.e("Headers", "Token de autenticação não encontrado"+token);
               }
               Map<String, String> headers = ApiConfig.configHeaders(token);
               Log.d("Headers", headers.toString());
               return headers;
           }
       };


       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(jsonObjectRequest);
   }

   public void readUser(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
       String url = ApiConfig.BASE_URL + "/users";

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
               Request.Method.GET,
               url,
               null,
               responseListener,
               errorListener){
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               String token = user.getToken();
               if (token == null) {
                   Log.e("Headers", "Token de autenticação não encontrado"+token);
               }
               Map<String, String> headers = ApiConfig.configHeaders(token);
               Log.d("Headers", headers.toString());
               return headers;
           }
       };


       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(jsonObjectRequest);
   }

    public void updateData(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = ApiConfig.BASE_URL + "/users/update";

        JSONObject UserData = new JSONObject();
        try {
            UserData.put("name", user.getName());
            UserData.put("telephone", user.getTelephone());
            UserData.put("email", user.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                UserData,
                responseListener,
                errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = user.getToken();
                if (token == null) {
                    Log.e("Headers", "Token de autenticação não encontrado"+token);
                }
                Map<String, String> headers = ApiConfig.configHeaders(token);
                Log.d("Headers", headers.toString());
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}
