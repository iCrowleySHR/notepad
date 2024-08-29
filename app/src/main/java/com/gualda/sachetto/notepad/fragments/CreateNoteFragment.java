package com.gualda.sachetto.notepad.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.model.Note;
import com.gualda.sachetto.notepad.service.NoteService;
import org.json.JSONObject;

public class CreateNoteFragment extends Fragment {

    Button btnCreateNote;
    EditText edtTitle, edtContent, edtCategory;
    Note note;

    public CreateNoteFragment() {
        // Required empty public constructor
    }

    public static CreateNoteFragment newInstance(String param1, String param2) {
        CreateNoteFragment fragment = new CreateNoteFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }

        // Inicializando a anotação
        note = new Note();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_note, container, false);

        btnCreateNote = view.findViewById(R.id.btnCreateNote);
        edtTitle = view.findViewById(R.id.edtTitle);
        edtContent = view.findViewById(R.id.edtContent);
        edtCategory = view.findViewById(R.id.edtCategory);

        btnCreateNote.setOnClickListener(v -> setData());

        return view;
    }

    private void setData() {
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();  // Corrigido para edtCategory

        if (title.isEmpty() || content.isEmpty() || category.isEmpty()) {
            Toast.makeText(CreateNoteFragment.this.getActivity(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Setando os valores na model
            note.setTitle(title);
            note.setContent(content);
            note.setCategory(category);
            this.sendData();
        }
    }

    private void sendData() {
        NoteService noteService = new NoteService(CreateNoteFragment.this.getActivity());
        noteService.createNote(note, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("data")) {
                    Toast.makeText(CreateNoteFragment.this.getActivity(), "Anotação criada!", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(CreateNoteFragment.this.getActivity(), "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("Exception", "Exception", e);
                    }
                } else {
                    Log.e("Error", "Erro desconhecido", error);
                    Toast.makeText(CreateNoteFragment.this.getActivity(), "Erro desconhecido", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
