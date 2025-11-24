package com.example.notesapp;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Application application){
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(application, AppDatabase.class, "notes-db")
                    .fallbackToDestructiveMigration().build();
        return INSTANCE;
    }
}
