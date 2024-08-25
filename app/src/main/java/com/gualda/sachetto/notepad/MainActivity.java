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
import com.gualda.sachetto.notepad.activities.Home;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;
import com.gualda.sachetto.notepad.utils.JWT;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnSubmit;
    TextView txtNotLogged;

    private UserService userService;
    private JWT jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }

        /* Verifica se já existe um token salvo*/
        jwt = new JWT();
        jwt.verifyIfExistToken(MainActivity.this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtNotLogged = findViewById(R.id.txtNotLogged);

        btnSubmit.setOnClickListener(v -> sendData());
    }

    private void sendData() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(email, password);
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
                        String id = data.optString("id", "ID não encontrado");
                        String name = data.optString("name", "Nome não encontrado");
                        String email = data.optString("email", "Email não encontrado");

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
}
