package com.gualda.sachetto.notepad;

import static com.gualda.sachetto.notepad.utils.NavigationUtil.*;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.activities.CreateAccount;
import com.gualda.sachetto.notepad.activities.Home;
import com.gualda.sachetto.notepad.databinding.ActivityMainBinding;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;
import com.gualda.sachetto.notepad.utils.JWT;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    UserService userService;
    JWT jwt;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }

        jwt = new JWT();
        /* Verifica se já existe um token salvo*/
        jwt.verifyIfExistToken(MainActivity.this);
        user = new User();

        binding.btnSubmit.setOnClickListener(v -> setData());
        binding.txtNotLogged.setOnClickListener(v -> navigateTo(this, CreateAccount.class));
    }

    private void setData(){
        String email    = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            // Setando os valores na model
            user.setEmail(email);
            user.setPassword(password);
            this.sendData();
        }
    }

    private void sendData() {
        userService = new UserService(this);
        userService.loginUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("data")) {
                    try {
                        String token = response.getString("token");
                        jwt.saveTokenJWT(MainActivity.this, token);

                        navigateTo(MainActivity.this,Home.class);

                        JSONObject data = response.getJSONObject("data");
                        String id = data.getString("id");
                        String name = data.getString("name");
                        String email = data.getString("email");

                        Toast.makeText(MainActivity.this, "Usuário autenticado!\nID: " + id + "\nName: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Erro ao processar dados", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(MainActivity.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
