package com.yoji.notebookwithsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText noteEditTxt;
    private Button saveNoteBtn;

    private SharedPreferences savedNoteShapedPrefs;
    private static String NOTE_TXT = "note_txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getDataFromSharedPrefs();
    }

    public void initViews(){
        noteEditTxt = findViewById(R.id.noteEditTxtId);
        saveNoteBtn = findViewById(R.id.saveNoteBtnId);
        savedNoteShapedPrefs = getSharedPreferences("Saved Note", MODE_PRIVATE);

        saveNoteBtn.setOnClickListener(saveNoteOnClickListener);
        noteEditTxt.addTextChangedListener(noteTextWatcher);
    }

    private View.OnClickListener saveNoteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = savedNoteShapedPrefs.edit();
            String savedNoteTxt = noteEditTxt.getText().toString().trim();
            editor.putString(NOTE_TXT, savedNoteTxt);
            editor.apply();
            Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_LONG).show();
        }
    };

    TextWatcher noteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String noteTxt = noteEditTxt.getText().toString();
            saveNoteBtn.setEnabled(!noteTxt.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void getDataFromSharedPrefs(){
        String savedNoteTxt = savedNoteShapedPrefs.getString(NOTE_TXT, "");
        noteEditTxt.setText(savedNoteTxt);
    }
}
