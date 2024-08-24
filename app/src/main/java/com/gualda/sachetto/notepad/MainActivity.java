package com.gualda.sachetto.notepad;

import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.Activities.CreateAccount;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;
import com.gualda.sachetto.notepad.utils.NavigationUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnSubmit;
    TextView txtNotLogged;


    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtNotLogged = findViewById(R.id.txtNotLogged);

        userService = new UserService(this);

        txtNotLogged.setOnClickListener(v -> NavigationUtil.navigateTo(this, CreateAccount.class));
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

        userService.loginUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response.has("data")) {

                    try {
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
