package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {


    private EditText noteEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteEditText = findViewById(R.id.note_text);

        //Added for assignment:
        String text = getIntent().getStringExtra("NOTE_TEXT");
        if(text != null){
            noteEditText.setText(text);
        }

        Button button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if(TextUtils.isEmpty(noteEditText.getText())) {
                    setResult(RESULT_CANCELED, i);
                    }
                else if(getIntent().hasExtra("NOTE_ID"){
                    i.putExtra("NOTE_ID", getIntent().getIntExtra("NOTE_ID", -1));
                    i.putExtra("NOTE_TEXT", noteEditText.getText().toString());
                    if(getIntent().hasExtra("NOTE_TEXT")){
                        setResult(RESULT_OK, i);
                    }
                } else {
                    setResult(RESULT_OK, i);
                    i.putExtra("NOTE_TEXT", noteEditText.getText().toString());
                }
                finish();
            }
        });
    }
}