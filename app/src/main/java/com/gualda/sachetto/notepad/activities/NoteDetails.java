package com.gualda.sachetto.notepad.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.databinding.ActivityNoteDetailsBinding;
import com.gualda.sachetto.notepad.service.NoteService;

import org.json.JSONObject;

public class NoteDetails extends AppCompatActivity {

    ActivityNoteDetailsBinding binding;
    NoteService noteService;
    String id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String category = intent.getStringExtra("category");
        id = intent.getStringExtra("id");

        Toast.makeText(this, "id: "+id, Toast.LENGTH_SHORT).show();

        binding.edtTitleView.setText(title);
        binding.edtCategoryView.setText(category);
        binding.edtContentView.setText(content);

        binding.btnDeleteNote.setOnClickListener(v -> deleteNote());
    }

    private void deleteNote(){
        noteService = new NoteService(this);
        noteService.deleteNote(id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("success")){
                    try{
                        String success = response.getString("success");
                        Toast.makeText(NoteDetails.this, "Mensagem: "+success, Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
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

                    Toast.makeText(NoteDetails.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}