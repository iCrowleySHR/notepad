package com.gualda.sachetto.notepad.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.adapter.NoteAdapter;
import com.gualda.sachetto.notepad.model.Note;
import com.gualda.sachetto.notepad.service.NoteService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ListView listViewNotes;
    private NoteAdapter adapter;
    private ArrayList<Note> notesList;
    private NoteService noteService;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listViewNotes = view.findViewById(R.id.listViewNotes);
        notesList = new ArrayList<>();
        adapter = new NoteAdapter(getContext(), notesList);
        listViewNotes.setAdapter(adapter);

        noteService = new NoteService(getContext());
        fetchNotes();

        return view;
    }

    private void fetchNotes() {
        noteService.readNote(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                notesList.clear();
                try {
                    JSONArray notesArray = response.getJSONArray("data");
                    for (int i = 0; i < notesArray.length(); i++) {
                        JSONObject noteObject = notesArray.getJSONObject(i);
                        String title = noteObject.getString("title");
                        String content = noteObject.getString("content");
                        String category = noteObject.optString("category", "Sem categoria");
                        
                        Note note = new Note();
                        note.setTitle(title);
                        note.setContent(content);
                        note.setCategory(category);
                        notesList.add(note);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Erro ao processar as notas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erro ao carregar notas: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
