package com.wochstudios.soundboard.Database.DAO;

import android.database.Cursor;
import android.database.sqlite.*;
import android.content.*;

public interface SoundboardDbDAO
{
	public boolean insert(SQLiteDatabase db, ContentValues v);
	public Cursor read();
	public void delete();
	public boolean update();
}
