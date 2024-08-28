package com.gualda.sachetto.notepad.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.MainActivity;
import com.gualda.sachetto.notepad.databinding.ActivityUpdatePasswordBinding;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;
import com.gualda.sachetto.notepad.utils.JWT;

import org.json.JSONObject;

public class UpdatePassword extends AppCompatActivity {

    private ActivityUpdatePasswordBinding binding;
    User user;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSendUpdatePass.setOnClickListener(v -> setData());
    }

    private void setData(){
        String currentPassword = binding.edtCurrentPassword.getText().toString().trim();
        String newPassword     = binding.edtNewPassword.getText().toString().trim();
        String newPasswordConfirmation = binding.edtNewPasswordConfirmation.getText().toString().trim();

        if(currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirmation.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            user = new User();

            user.setPassword(currentPassword);
            user.setNewPassword(newPassword);
            user.setNewPasswordConfirmation(newPasswordConfirmation);
            sendData();
        }
    }

    private void sendData(){
        userService = new UserService(this);
        userService.updatePasswordUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.has("message")){
                        String message = response.getString("message");
                        Toast.makeText(UpdatePassword.this, "Mensagem: "+message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch(Exception e){
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

                    Toast.makeText(UpdatePassword.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}