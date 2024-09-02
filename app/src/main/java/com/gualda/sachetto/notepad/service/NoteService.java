package com.gualda.sachetto.notepad.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.gualda.sachetto.notepad.model.Note;
import com.gualda.sachetto.notepad.model.User;
import com.gualda.sachetto.notepad.utils.JWT;

import org.json.JSONException;
import org.json.JSONObject;

public class NoteService {
    private final Context context;
    private final UtilsService utilsService;

    public NoteService(Context context) {
        this.context = context;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        this.utilsService = new UtilsService(requestQueue);
    }

    public void createNote(Note note, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = ApiConfig.BASE_URL + "/notes";
        String token = getJwtToken();

        JSONObject noteData = new JSONObject();
        try {
            noteData.put("title", note.getTitle());
            noteData.put("content", note.getContent());
            noteData.put("category", note.getCategory());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao criar JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        utilsService.makeRequest(Request.Method.POST, url, noteData, responseListener, errorListener, token);
    }

    public void readNote(Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        String url = ApiConfig.BASE_URL + "/notes";
        String token = getJwtToken();

        utilsService.makeRequest(Request.Method.GET, url, null, responseListener, errorListener, token);
    }

    public void deleteNote(String id,Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        String url = ApiConfig.BASE_URL + "/notes/delete/" + id;
        String token = getJwtToken();

        utilsService.makeRequest(Request.Method.DELETE, url, null, responseListener, errorListener, token);
    }

    public void updateNote(String id,Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        String url = ApiConfig.BASE_URL + "/notes/update/" + id;
        String token = getJwtToken();

        utilsService.makeRequest(Request.Method.PUT, url, null, responseListener, errorListener, token);
    }

    private String getJwtToken() {
        JWT jwt = new JWT();
        return jwt.getJwtToken(context);
    }
}
