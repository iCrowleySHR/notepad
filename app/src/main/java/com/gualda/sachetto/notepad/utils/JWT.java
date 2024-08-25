package com.gualda.sachetto.notepad.utils;

import static com.gualda.sachetto.notepad.utils.NavigationUtil.navigateTo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.activities.Home;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;

import org.json.JSONObject;

public class JWT {

    private static final String TAG = "JWT";
    private static final String PREFERENCES_NAME = "Token";
    private static final String TOKEN_KEY = "jwt_token";

    public void saveTokenJWT(Context context, String token) {
        if (token == null || token.isEmpty()) {
            Log.e(TAG, "Tentativa de salvar um token nulo ou vazio");
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        boolean success = editor.commit();
        Log.d(TAG, "Token salvo: " + token + ", sucesso: " + success);
    }

    public String getJwtToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);
        Log.d(TAG, "Token recuperado: " + token);
        return token;
    }

    public void destroyTokenJWT(Context context) {
        // Faz logout da API
        User user = new User();
        user.setToken(this.getJwtToken(context));

        UserService userService = new UserService(context);
        userService.logoutUser(user,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("success")) {
                    try {
                        String message = response.getString("success");
                        Log.d(TAG, "Resposta do logout da API: " + message);
                        Toast.makeText(context, "Mensagem: " + message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "Erro ao processar resposta do logout", e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String errorBody = new String(error.networkResponse.data, "UTF-8");
                    JSONObject errorJson = new JSONObject(errorBody);
                    String errorMessage = errorJson.getString("message");
                    Toast.makeText(context, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Erro ao processar resposta de erro do logout", e);
                }
            }
        });

        // Remove o token da memória do dispositivo
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        boolean success = editor.commit();
        Log.d(TAG, "Token removido: " + success);
    }

    public void verifyIfExistToken(Context context) {
        String token = getJwtToken(context);
        Log.e(TAG, "Token se existe: " + token);
        if (token != null && !token.isEmpty()) {
            navigateTo(context, Home.class);
        } else {
            Toast.makeText(context, "Token não encontrado ou inválido", Toast.LENGTH_SHORT).show();
        }
    }
}