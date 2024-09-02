package com.gualda.sachetto.notepad.activities;

import static com.gualda.sachetto.notepad.utils.NavigationUtil.navigateTo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.databinding.ActivityNoteDetailsBinding;
import com.gualda.sachetto.notepad.fragments.CreateNoteFragment;
import com.gualda.sachetto.notepad.fragments.HomeFragment;
import com.gualda.sachetto.notepad.model.Note;
import com.gualda.sachetto.notepad.service.NoteService;
import com.gualda.sachetto.notepad.utils.NavigationUtil;

import org.json.JSONObject;

public class NoteDetails extends AppCompatActivity {

    ActivityNoteDetailsBinding binding;
    NoteService noteService;
    Note note;
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

        note = new Note();
        noteService = new NoteService(this);

        binding.btnDeleteNote.setOnClickListener(v -> deleteNote());
        binding.btnUpdateNote.setOnClickListener(v -> setData());
    }

    private void deleteNote(){
        noteService.deleteNote(id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("success")){
                    try{
                        String success = response.getString("success");
                        Toast.makeText(NoteDetails.this, "Mensagem: "+success, Toast.LENGTH_SHORT).show();
                        navigateTo(NoteDetails.this, Home.class);
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

    private void setData(){
        String title = binding.edtTitleView.getText().toString().trim();
        String content = binding.edtContentView.getText().toString().trim();
        String category = binding.edtCategoryView.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            note.setTitle(title);
            note.setContent(content);
            note.setCategory(category);
            note.setIdNote(id);
            this.updateNote();
        }
    }

    private void updateNote(){
        noteService.updateNote(note, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("data")) {
                    Toast.makeText(NoteDetails.this, "Anotação atualizada!", Toast.LENGTH_SHORT).show();
                    navigateTo(NoteDetails.this, Home.class);
                }else{
                    Toast.makeText(NoteDetails.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String errorBody = new String(error.networkResponse.data, "UTF-8");
                        JSONObject errorJson = new JSONObject(errorBody);
                        String errorMessage = errorJson.getString("message");

                        Toast.makeText(NoteDetails.this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("Exception", "Exception", e);
                    }
                } else {
                    Log.e("Error", "Erro desconhecido", error);
                    Toast.makeText(NoteDetails.this, "Erro desconhecido", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}