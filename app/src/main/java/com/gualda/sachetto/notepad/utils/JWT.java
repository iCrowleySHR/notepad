package com.gualda.sachetto.notepad.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class JWT {

    public void saveTokenJWT(Context context,String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.apply();
    }

    public String getJwtToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwt_token", null); // Retorna null se o token não estiver presente
    }

    public void destroyTokenJWT(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("jwt_token");
        editor.apply();
    }


}
