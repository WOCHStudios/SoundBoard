package com.wochstudios.soundboard.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.*;

public interface ISoundboardDAO
{
	public boolean insert(SQLiteDatabase db, String table, ContentValues v);
	public Cursor read(SQLiteDatabase db,String table, String[] select, String where, String[] whereValues,String order);
	public void delete();
	public boolean update();
}
