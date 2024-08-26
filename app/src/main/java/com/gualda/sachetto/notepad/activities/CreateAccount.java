package com.gualda.sachetto.notepad.activities;

import static com.gualda.sachetto.notepad.utils.NavigationUtil.navigateTo;

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
import com.gualda.sachetto.notepad.MainActivity;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.service.UserService;

import org.json.JSONObject;

public class CreateAccount extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtBirthDate, edtTelephone, edtName;
    Button btnSubmit;

    User user = new User();
    UserService userService = new UserService(this);

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

        edtEmail     = findViewById(R.id.edtEmailUpdate);
        edtName      = findViewById(R.id.edtNameUpdate);
        edtBirthDate = findViewById(R.id.edtBirthDateUpdate);
        edtPassword  = findViewById(R.id.edtPasswordUpdate);
        edtTelephone = findViewById(R.id.edtTelephoneUpdate);
        btnSubmit    = findViewById(R.id.btnSubmitUpdate);

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
            user.setEmail(email);
            user.setPassword(password);
            user.setBirthDate(birthDate);
            user.setTelephone(telephone);
            user.setName(name);
            this.sendData();
        }
    }

    private void sendData(){
        userService.RegisterUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("data")) {
                    Toast.makeText(CreateAccount.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                    navigateTo(CreateAccount.this, MainActivity.class);
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