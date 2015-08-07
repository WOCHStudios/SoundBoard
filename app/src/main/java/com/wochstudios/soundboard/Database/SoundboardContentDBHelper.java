package com.wochstudios.soundboard.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dave on 8/6/2015.
 */
public class SoundboardContentDBHelper extends SQLiteOpenHelper {

    public SoundboardContentDBHelper(Context context ){
        super(context,"", null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
