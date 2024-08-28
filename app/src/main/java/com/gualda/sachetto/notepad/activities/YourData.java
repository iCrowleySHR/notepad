package com.gualda.sachetto.notepad.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.MainActivity;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.databinding.ActivityHomeBinding;
import com.gualda.sachetto.notepad.databinding.ActivityYourDataBinding;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;

import org.json.JSONObject;

public class YourData extends AppCompatActivity {

    UserService userService;
    ActivityYourDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYourDataBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        userService = new UserService(this);
        userService.readUser(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");

                    if(response.has("data")){
                        binding.txtName.setText(data.getString("name"));
                        binding.txtEmail.setText(data.getString("email"));
                        binding.txtBirthDate.setText(data.getString("birth_date"));
                        binding.txtTelephone.setText(data.getString("telephone"));
                        binding.txtUpdateAt.setText(data.getString("updated_at"));
                        binding.txtCreatedAt.setText(data.getString("created_at"));
                    }
                }catch (Exception e){
                    Log.e("Exception","Exception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String errorBody = new String(error.networkResponse.data, "UTF-8");
                    JSONObject errorJson = new JSONObject(errorBody);

                    String errorMessage = errorJson.getString("message");

                    Toast.makeText(YourData.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Log.e("Exception","Exception", e);
                }
            }
        });

    }
}