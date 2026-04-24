package com.example.mobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editContent;
    private long noteId = -1L;
    private boolean isNew = true;
    private int selectedCategory = 0;

    private ImageButton[] iconButtons = new ImageButton[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        Button buttonSave = findViewById(R.id.button_save);
        Button buttonCancel = findViewById(R.id.button_cancel);

        iconButtons[0] = findViewById(R.id.icon1);
        iconButtons[1] = findViewById(R.id.icon2);
        iconButtons[2] = findViewById(R.id.icon3);

        // Wire up each category button to set selectedCategory
        for (int i = 0; i < iconButtons.length; i++) {
            final int index = i;
            iconButtons[i].setOnClickListener(v -> selectCategory(index));
        }

        isNew = getIntent().getBooleanExtra("isNew", true);
        noteId = getIntent().getLongExtra("id", -1L);

        if (!isNew) {
            editTitle.setText(getIntent().getStringExtra("title"));
            editContent.setText(getIntent().getStringExtra("content"));
            selectedCategory = getIntent().getIntExtra("category", 0);
            setTitle("Edit note");
        } else {
            setTitle("New note");
        }

        selectCategory(selectedCategory); // highlight the initial selection

        buttonSave.setOnClickListener(v -> saveNote());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void selectCategory(int index) {
        selectedCategory = index;
        // Visual feedback: highlight the selected button, dim others
        for (int i = 0; i < iconButtons.length; i++) {
            if (i == index) {
                iconButtons[i].setBackgroundColor(Color.LTGRAY);
            } else {
                iconButtons[i].setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private void saveNote() {
        String titleText = editTitle.getText().toString().trim();
        String contentText = editContent.getText().toString().trim();

        if (titleText.isEmpty() && contentText.isEmpty()) {
            Toast.makeText(this, "Please enter a title or content", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent result = new Intent();
        result.putExtra("id", noteId);
        result.putExtra("title", titleText);
        result.putExtra("content", contentText);
        result.putExtra("category", selectedCategory);
        result.putExtra("isNew", isNew);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}