package com.wochstudios.soundboard.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wochstudios.soundboard.Interfaces.ISoundboardDAO;

public class SoundboardDAO implements ISoundboardDAO
{

	public SoundboardDAO(){}
	
	@Override
	public boolean insert(SQLiteDatabase db, String table,ContentValues v)
	{
		long result = db.insertOrThrow(table, null,v);
		return (result < 0);
	}

	@Override
	public Cursor read(SQLiteDatabase db, String table, String[] select, String where, String[] whereValues,String order)
	{
		Cursor c = db.query(table, select, where, whereValues, null, null, order);
		return c;
	}

	@Override
	public boolean delete(SQLiteDatabase db, String table, String selection, String[] args)
	{
		int result =db.delete(table,selection,args);
		return (result > 0);
	}

	@Override
	public boolean update(SQLiteDatabase db, ContentValues values, String table, String selection, String[] args)
	{
		int result = db.update(table, values,selection,args);
		return (result > 0);
	}

}
