package com.gualda.sachetto.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gualda.sachetto.notepad.R;
import com.gualda.sachetto.notepad.model.Note;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> notesList;

    public NoteAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        }

        TextView textTitle = convertView.findViewById(R.id.textTitle);
        TextView textContent = convertView.findViewById(R.id.textContent);
        TextView textCategory = convertView.findViewById(R.id.textCategory);

        Note note = notesList.get(position);

        textTitle.setText(note.getTitle());
        textContent.setText(note.getContent());
        textCategory.setText(note.getCategory());

        return convertView;
    }
}
