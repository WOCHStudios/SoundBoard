package com.wochstudios.soundboard.Interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface ISoundboardDAO
{
	public boolean insert(SQLiteDatabase db, String table, ContentValues v);
	public Cursor read(SQLiteDatabase db,String table, String[] select, String where, String[] whereValues,String order);
	public boolean delete(SQLiteDatabase db, String table, String selection, String[] args);
	public boolean update(SQLiteDatabase db, ContentValues values, String table, String selection, String[] args);
}
