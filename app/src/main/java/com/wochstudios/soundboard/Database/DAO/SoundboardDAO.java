package com.wochstudios.soundboard.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wochstudios.soundboard.Database.SounboardContract.SoundboardsTable;

public class SoundboardDAO implements ISoundboardDAO
{

	public SoundboardDAO(){}
	
	@Override
	public boolean insert(SQLiteDatabase db, String table,ContentValues v)
	{
		//long result = 0;
		long result = db.insert(table, null,v);		
		return (result < 0);
	}

	@Override
	public Cursor read(SQLiteDatabase db, String table, String[] select, String where, String[] whereValues,String order)
	{
		Cursor c = db.query(table, select, where, whereValues, null, null, order);
		return c;
	}

	@Override
	public void delete()
	{
		// TODO: Implement this method
	}

	@Override
	public boolean update()
	{
		// TODO: Implement this method
		return false;
	}

}
