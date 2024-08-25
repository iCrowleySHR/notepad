package com.gualda.sachetto.notepad.utils;


import static com.gualda.sachetto.notepad.utils.NavigationUtil.navigateTo;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.gualda.sachetto.notepad.MainActivity;
import com.gualda.sachetto.notepad.activities.Home;

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

    public void verifyIfExistToken(Context context){
        String token = this.getJwtToken(context);
        if (token != null && !token.isEmpty()) {
            navigateTo(context, Home.class);
        } else {
            Toast.makeText(context, "Token não encontrado ou inválido", Toast.LENGTH_SHORT).show();
        }
    }

}
