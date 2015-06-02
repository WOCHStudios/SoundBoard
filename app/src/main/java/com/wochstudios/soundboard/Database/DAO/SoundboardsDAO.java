package com.wochstudios.soundboard.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wochstudios.soundboard.Database.SounboardContract.SoundboardsTable;

public class SoundboardsDAO implements SoundboardDbDAO
{

	@Override
	public boolean insert(SQLiteDatabase db, ContentValues v)
	{
		long result = db.insert(SoundboardsTable.TABLE_NAME, null,v);		
		return (result < 0)? false:true;
	}

	@Override
	public Cursor read()
	{
		// TODO: Implement this method
		return null;
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
