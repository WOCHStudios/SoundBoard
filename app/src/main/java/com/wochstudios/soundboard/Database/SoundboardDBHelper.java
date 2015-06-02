package com.wochstudios.soundboard.Database;

import com.wochstudios.soundboard.Database.SounboardContract.*;
import android.database.sqlite.*;
import android.content.*;
import com.wochstudios.soundboard.Database.DAO.*;

public class SoundboardDBHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Soundboard.db";
	
	private SoundboardDbDAO sbDAO;
	
	public SoundboardDBHelper (Context con){
		super(con,DATABASE_NAME, null, DATABASE_VERSION); 
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(SoundboardsTable.SQL_CREATE_TABLE_SOUNDBOARDS);
		db.execSQL(SoundsTable.SQL_CREATE_TABLE_SOUNDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
		db.execSQL(SoundsTable.SQL_DELETE_TABLE_SOUNDS);
		db.execSQL(SoundboardsTable.SQL_DELETE_TABLE_SOUNDBOARDS);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
		onUpgrade(db, oldVersion, newVersion);
	}
	
	
	public void insertIntoDatabase(String dbName){
		
	}
	
	private void insertIntoSounds(){
		sbDAO = new SoundsDAO();
		
	}
	private void insertIntoSoundboards(){
		sbDAO = new SoundboardsDAO();
	}
	
}
