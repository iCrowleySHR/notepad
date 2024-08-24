package com.gualda.sachetto.notepad.Activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gualda.sachetto.notepad.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccount extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtBirthDate, edtTelephone, edtName;
    Button btnRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }

        edtEmail     = findViewById(R.id.edtEmail);
        edtPassword  = findViewById(R.id.edtPassword);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtName      = findViewById(R.id.edtName);
        btnRegister  = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> sendData());
    }

    private void sendData() {
        String email     = edtEmail.getText().toString().trim();
        String password  = edtPassword.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();
        String telephone = edtPassword.getText().toString().trim();
        String name      = edtName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || telephone.isEmpty() || birthDate.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // URL da API
        String url = "http://192.168.1.10/api_notepad/public/api/v1/users/";

        // Criar o objeto JSON com os dados do usuário
        JSONObject createUser = new JSONObject();
        try {
            createUser.put("email", email);
            createUser.put("password", password);
            createUser.put("name", name);
            createUser.put("telephone", telephone);
            createUser.put("birth_date", birthDate);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar uma nova requisição JSON
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, // Método POST
                url,                 // URL
                createUser,           // Dados a serem enviados
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(CreateAccount.this, "resposta:"+ response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAccount.this, "Erro ao enviar dados: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Adicionar a requisição à fila de requisições
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}