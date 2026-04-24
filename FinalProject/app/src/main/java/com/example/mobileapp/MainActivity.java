package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Request code used to identify results coming back from NoteDetailActivity
    private static final int REQUEST_NOTE = 1;

    private List<Note> noteItems = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private TextView emptyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    return insets;
                });

        ListView notesListView = findViewById(R.id.list_notes);
        FloatingActionButton addBtn = findViewById(R.id.fab_add);
        SwitchCompat compactSwitch = findViewById(R.id.switch_compact);
        emptyLabel = findViewById(R.id.text_empty);

        noteAdapter = new NoteAdapter(this, noteItems, this::askBeforeDelete);
        notesListView.setAdapter(noteAdapter);

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Note selected = noteItems.get(position);
            launchNoteEditor(selected);
        });

        compactSwitch.setOnCheckedChangeListener((btn, checked) -> {
            noteAdapter.setCompactMode(checked);
            noteAdapter.notifyDataSetChanged();
        });

        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, NoteDetailActivity.class);
            i.putExtra("isNew", true);
            startActivityForResult(i, REQUEST_NOTE);
        });

        noteItems.add(
                new Note(1L, "Welcome!", "Click the + button to make a new note", 0));
        noteAdapter.notifyDataSetChanged();

        refreshEmptyState();
    }

    // Old-style result handler — fires when a child activity finishes with setResult()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NOTE && resultCode == RESULT_OK && data != null) {
            String t = data.getStringExtra("title");
            String c = data.getStringExtra("content");
            long noteId = data.getLongExtra("id", -1);
            boolean brandNew = data.getBooleanExtra("isNew", false);
            int cat = data.getIntExtra("category", 0);

            if (brandNew) {
                Note newNote = new Note(System.currentTimeMillis(), t, c, cat);
                noteItems.add(0, newNote);
                Toast.makeText(this, "Note created", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < noteItems.size(); i++) {
                    Note n = noteItems.get(i);
                    if (n.getId() == noteId) {
                        noteItems.set(i, new Note(noteId, t, c, cat));
                        break;
                    }
                }
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            }

            noteAdapter.notifyDataSetChanged();
            refreshEmptyState();
        }
    }

    private void launchNoteEditor(Note n) {
        Intent i = new Intent(this, NoteDetailActivity.class);
        i.putExtra("id", n.getId());
        i.putExtra("title", n.getTitle());
        i.putExtra("content", n.getContent());
        i.putExtra("category", n.getCategory());
        i.putExtra("isNew", false);
        startActivityForResult(i, REQUEST_NOTE);
    }

    private void askBeforeDelete(Note n) {
        new AlertDialog.Builder(this)
                .setTitle("Delete note")
                .setMessage("Are you sure you want to delete \"" + n.getTitle() + "\"?")
                .setPositiveButton("Delete",
                        (dialog, which) -> {
                            noteItems.remove(n);
                            noteAdapter.notifyDataSetChanged();
                            refreshEmptyState();
                            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                        })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void refreshEmptyState() {
        if (noteItems.isEmpty()) {
            emptyLabel.setVisibility(View.VISIBLE);
        } else {
            emptyLabel.setVisibility(View.GONE);
        }
    }
}