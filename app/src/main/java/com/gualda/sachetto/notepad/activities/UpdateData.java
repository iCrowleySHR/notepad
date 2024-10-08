package com.gualda.sachetto.notepad.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.MainActivity;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.databinding.ActivityUpdateDataBinding;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;
import com.gualda.sachetto.notepad.utils.JWT;

import org.json.JSONObject;

public class UpdateData extends AppCompatActivity {

    ActivityUpdateDataBinding binding;
    UserService userService;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateDataBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        userService = new UserService(this);
        userService.readUser( new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("data")){
                    try{
                        JSONObject data = response.getJSONObject("data");

                        binding.edtUpdateEmail.setText(data.getString("name"));
                        binding.edtUpdateName.setText(data.getString("email"));
                        binding.edtUpdateTelephone.setText(data.getString("telephone"));
                    }catch (Exception e){
                        Log.e("Exception","Exception", e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String errorBody = new String(error.networkResponse.data, "UTF-8");
                    JSONObject errorJson = new JSONObject(errorBody);

                    String errorMessage = errorJson.getString("message");

                    Toast.makeText(UpdateData.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        binding.btnUpdateData.setOnClickListener(v -> setData());
    }

    private void setData(){
        String email     = binding.edtUpdateEmail.getText().toString().trim();
        String name      = binding.edtUpdateName.getText().toString().trim();
        String telephone = binding.edtUpdateTelephone.getText().toString().trim();

        if(email.isEmpty() || name.isEmpty() || telephone.isEmpty()){
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            user.setEmail(email);
            user.setTelephone(telephone);
            user.setName(name);
            this.sendData();
        }
    }

    private void sendData(){
        userService.updateData(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.has("message")){
                        String message = response.getString("message");
                        Toast.makeText(UpdateData.this, "Mensagem: "+message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String errorBody = new String(error.networkResponse.data, "UTF-8");
                    JSONObject errorJson = new JSONObject(errorBody);

                    String errorMessage = errorJson.getString("message");

                    Toast.makeText(UpdateData.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}