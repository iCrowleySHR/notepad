package com.gualda.sachetto.notepad.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gualda.sachetto.notepad.CreateNoteFragment;
import com.gualda.sachetto.notepad.HomeFragment;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.SettingsFragment;
import com.gualda.sachetto.notepad.databinding.ActivityHomeBinding;
import com.gualda.sachetto.notepad.utils.JWT;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_nav) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.create_note_nav) {
                replaceFragment(new CreateNoteFragment());
            } else if (itemId == R.id.settings_nav) {
                replaceFragment(new SettingsFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}