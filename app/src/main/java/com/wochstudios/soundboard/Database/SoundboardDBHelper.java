
package com.wochstudios.soundboard.Database;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;

import com.wochstudios.soundboard.Database.DAO.*;
import com.wochstudios.soundboard.Database.SounboardContract.*;
import com.wochstudios.soundboard.Models.*;

public class SoundboardDBHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Soundboard.db";
	public static final String SOUNDS_TABLE_CODE = "sounds";
	public static final String SOUNDBOARDS_TABLE_CODE = "soundboards";
	
	private ISoundboardDAO sbDAO;
	
	public SoundboardDBHelper (Context con){
		super(con,DATABASE_NAME, null, DATABASE_VERSION); 
		sbDAO = new SoundboardDAO();
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
	
	
	public void insertIntoDatabase(String table, ContentValues values ){
		switch (table){
			case SOUNDS_TABLE_CODE:
				sbDAO.insert(this.getWritableDatabase(),SoundsTable.TABLE_NAME, values);
				break;
			case SOUNDBOARDS_TABLE_CODE:
				sbDAO.insert(this.getWritableDatabase(),SoundboardsTable.TABLE_NAME, values);
				break;
			default:
				break;
		}
	}
	

	public Soundboard findSoundboard(String id){
		Soundboard sb = new Soundboard();
		Cursor c = sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME, null, SoundboardsTable._ID+" = ?", new String[]{id}, null);
		while(c.moveToNext()){
			sb.setID(Integer.parseInt(c.getString(c.getColumnIndex(SoundboardsTable._ID))));
			sb.setName(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_NAME)));
			sb.setDate_created(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_DATE_CREATED)));
		}
		return sb;
	}
	
	public Sound findSound(){
		String id = null,title = null, uri = null, soundboard_id = null;
		Cursor c = sbDAO.read(this.getReadableDatabase(), SoundsTable.TABLE_NAME,null,SoundsTable._ID+" =?", new String[]{"1"},null);
		while(c.moveToNext()){
			id = c.getString(c.getColumnIndex(SoundsTable._ID));
			title = c.getString(c.getColumnIndex(SoundsTable.COLUMN_TITLE));
			uri = c.getString(c.getColumnIndex(SoundsTable.COLUMN_URI));
			soundboard_id = c.getString(c.getColumnIndex(SoundsTable.COLUMN_SOUNDBOARD_ID));
		}
		return new Sound(Integer.parseInt(id),title,Uri.parse(uri),soundboard_id);
	}
	
}
