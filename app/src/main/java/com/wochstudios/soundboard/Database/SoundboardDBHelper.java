package com.wochstudios.soundboard.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wochstudios.soundboard.Database.DAO.SoundboardDbDAO;
import com.wochstudios.soundboard.Database.DAO.SoundboardsDAO;
import com.wochstudios.soundboard.Database.DAO.SoundsDAO;
import com.wochstudios.soundboard.Database.SounboardContract.SoundboardsTable;
import com.wochstudios.soundboard.Database.SounboardContract.SoundsTable;

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
