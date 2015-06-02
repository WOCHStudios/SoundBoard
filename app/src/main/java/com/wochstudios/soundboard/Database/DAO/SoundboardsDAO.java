package com.wochstudios.soundboard.Database.DAO;

import android.database.*;
import android.database.sqlite.*;
import android.content.*;

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
