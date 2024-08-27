package com.gualda.sachetto.notepad.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.gualda.sachetto.notepad.databinding.ActivityUpdatePasswordBinding;
import com.gualda.sachetto.notepad.model.User;

public class UpdatePassword extends AppCompatActivity {

    private ActivityUpdatePasswordBinding binding;
    User user;

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

        user = new User();

        user.setPassword(currentPassword);
        user.setNewPassword(newPassword);
        user.setNewPasswordConfirmation(newPasswordConfirmation);

        sendData();
    }

    private void sendData(){

    }
}