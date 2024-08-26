package com.gualda.sachetto.notepad.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.gualda.sachetto.notepad.R;

public class UpdateAccount extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtCurrentPassword, edtTelephone, edtName;
    Button btnSend;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account);

        edtEmail     = findViewById(R.id.edtEmailUpdate);
        edtName      = findViewById(R.id.edtNameUpdate);
        edtTelephone = findViewById(R.id.edtTelephoneUpdate);
        edtPassword  = findViewById(R.id.edtPasswordUpdate);
        edtCurrentPassword = findViewById(R.id.edtCurrentPassword);


    }
}