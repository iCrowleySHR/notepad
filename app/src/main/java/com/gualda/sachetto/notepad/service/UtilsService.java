package com.gualda.sachetto.notepad.service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class UtilsService {

    private final RequestQueue requestQueue;

    public UtilsService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue; // Corrigido para usar o parâmetro injetado
    }

    public void makeRequest(int method, String url, JSONObject data, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener, String token) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                method,
                url,
                data,
                responseListener,
                errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getAuthHeaders(token);
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private Map<String, String> getAuthHeaders(String token) {
        if (token == null) {
            Log.e("Headers", "Token de autenticação não encontrado");
        }
        Map<String, String> headers = ApiConfig.configHeaders(token);
        Log.d("Headers", headers.toString());
        return headers;
    }
}
