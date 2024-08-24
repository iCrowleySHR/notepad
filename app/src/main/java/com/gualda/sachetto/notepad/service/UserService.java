package com.gualda.sachetto.notepad.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gualda.sachetto.notepad.MainActivity;
import com.gualda.sachetto.notepad.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService {
    private Context context;

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, loginData,
                responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}
