package com.wochstudios.soundboard.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface SoundboardDbDAO
{
	public boolean insert(SQLiteDatabase db, ContentValues v);
	public Cursor read();
	public void delete();
	public boolean update();
}
