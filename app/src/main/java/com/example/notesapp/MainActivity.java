package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.ItemClickListener {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int UPDATE_NOTE_REQUEST = 2;

    private RecyclerView noteListView;
    private FloatingActionButton fab;
    private NoteViewModel viewModel;
    private Note lastClickedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteListView = findViewById(R.id.note_list);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        adapter.setItemClickListener(this);
        noteListView.setAdapter(adapter);
        noteListView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(i, ADD_NOTE_REQUEST);
            }
        });

        //TODO: setup view model
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            Note note = new Note();
            note.setNoteText(data.getStringExtra("NOTE_TEXT"));
            //TODO: call view model insert
            viewModel.insert(note);
        }
        //TODO: For the assignment you can add the case of handling a note update here:

    }

    @Override
    public void onItemClicked(Note note) {
        lastClickedNote = note;
            Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
            i.putExtra("NOTE_ID", lastClickedNote.getId());
            i.putExtra("NOTE_TEXT", lastClickedNote.getNoteText());
            startActivityForResult(i, ADD_NOTE_REQUEST);
    }

    @Override
    public void onItemLongClicked(Note note) {
        // You can react to a long press on a note by doing sth here
    }
}