package com.example.notesapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> notes;


    public NoteRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        noteDao = db.noteDao();
        notes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return notes;
    }

    public void insert(final Note note){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                noteDao.insertNote(note);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void delete(final Note note){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                noteDao.deleteNote(note);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void update(final Note note){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                noteDao.updateNote(note);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


}
