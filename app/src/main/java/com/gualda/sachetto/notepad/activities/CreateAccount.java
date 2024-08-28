package com.gualda.sachetto.notepad.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;

import org.json.JSONObject;

public class CreateAccount extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtBirthDate, edtTelephone, edtName;
    Button btnSubmit;
    User user;
    UserService userService;



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

        edtEmail     = findViewById(R.id.edtEmailCreate);
        edtName      = findViewById(R.id.edtNameCreate);
        edtBirthDate = findViewById(R.id.edtBirthDateCreate);
        edtPassword  = findViewById(R.id.edtPasswordCreate);
        edtTelephone = findViewById(R.id.edtTelephoneCreate);
        btnSubmit    = findViewById(R.id.btnSubmitCreate);

        btnSubmit.setOnClickListener(v -> setData());
    };

    private void setData(){
        String email     = edtEmail.getText().toString().trim();
        String name      = edtName.getText().toString().trim();
        String password  = edtPassword.getText().toString().trim();
        String telephone = edtTelephone.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();

        if(email.isEmpty() || name.isEmpty() || password.isEmpty() || telephone.isEmpty() || birthDate.isEmpty()){
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setBirthDate(birthDate);
            user.setTelephone(telephone);
            user.setName(name);
            this.sendData();
        }
    }

    private void sendData(){
        userService = new UserService(this);
        userService.registerUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("data")) {
                    Toast.makeText(CreateAccount.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String errorBody = new String(error.networkResponse.data, "UTF-8");
                    JSONObject errorJson = new JSONObject(errorBody);
                    String errorMessage = errorJson.getString("message");

                    Toast.makeText(CreateAccount.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}