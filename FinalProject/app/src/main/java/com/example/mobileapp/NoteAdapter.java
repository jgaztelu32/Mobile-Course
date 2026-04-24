package com.example.mobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    public interface Listener {
        void onDelete(Note note);
    }

    LayoutInflater mInflater;
    List<Note> notes;
    Listener listener;
    boolean compactMode = false;

    public NoteAdapter(Context c, List<Note> n, Listener l) {
        notes = n;
        listener = l;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCompactMode(boolean compact) {
        compactMode = compact;
    }

    // Maps a category number to an icon drawable — same pattern as teacher's getImg()
    public static int getImg(int category) {
        switch (category) {
            case 0: return R.drawable.shopping;
            case 1: return R.drawable.briefcase;
            case 2: return R.drawable.idea;
            default: return R.drawable.shopping;
        }
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return notes.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_note, parent, false);
        }

        Note note = notes.get(i);

        TextView title = view.findViewById(R.id.text_title);
        TextView content = view.findViewById(R.id.text_content);
        ImageButton deleteButton = view.findViewById(R.id.button_delete);
        ImageView categoryIcon = view.findViewById(R.id.image_category);

        title.setText(note.getTitle());
        content.setText(note.getContent());
        content.setVisibility(compactMode ? View.GONE : View.VISIBLE);
        categoryIcon.setImageResource(getImg(note.getCategory()));

        deleteButton.setOnClickListener(v -> listener.onDelete(note));

        return view;
    }
}