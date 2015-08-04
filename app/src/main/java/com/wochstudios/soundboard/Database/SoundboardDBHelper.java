
package com.wochstudios.soundboard.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.wochstudios.soundboard.Database.DAO.SoundboardDAO;
import com.wochstudios.soundboard.Database.SounboardContract.SoundboardsTable;
import com.wochstudios.soundboard.Database.SounboardContract.SoundsTable;
import com.wochstudios.soundboard.Interfaces.ISoundboardDAO;
import com.wochstudios.soundboard.Models.Sound;
import com.wochstudios.soundboard.Models.Soundboard;

import java.util.ArrayList;

public class SoundboardDBHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Soundboard.db";
	
	
	private ISoundboardDAO sbDAO;
	private Cursor c;
	
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
		sbDAO.insert(this.getWritableDatabase(),table, values);
	}
	
	public void removeFromDatabase(String table,String id){
		sbDAO.delete(this.getWritableDatabase(),table,BaseColumns._ID+" =?",new String[]{id});
	}
	

	public Soundboard findSoundboard(String id){
		Soundboard sb = new Soundboard();
		c = sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME, null, SoundboardsTable._ID+" = ?", new String[]{id}, null);
		while(c.moveToNext()){
			sb.setID(Integer.parseInt(c.getString(c.getColumnIndex(SoundboardsTable._ID))));
			sb.setTitle(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_NAME)));
			sb.setDate_created(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_DATE_CREATED)));
		}
		c.close();
		sb.setSounds(getSoundsForSoundboard(sb));
		return sb;
	}
	
	
	public Soundboard findSoundboardFromTitle(String title){
		Soundboard sb = new Soundboard();
		c = sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME, null, SoundboardsTable.COLUMN_NAME+" = ?", new String[]{title}, null);
		while(c.moveToNext()){
			sb.setID(Integer.parseInt(c.getString(c.getColumnIndex(SoundboardsTable._ID))));
			sb.setTitle(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_NAME)));
			sb.setDate_created(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_DATE_CREATED)));
		}
		c.close();
		sb.setSounds(getSoundsForSoundboard(sb));
		return sb;
	}
	
	public Sound findSound(){
		Sound s = new Sound();
		c = sbDAO.read(this.getReadableDatabase(), SoundsTable.TABLE_NAME,null,SoundsTable._ID+" =?", new String[]{"1"},null);
		while(c.moveToNext()){
			s.setID(Integer.parseInt(c.getString(c.getColumnIndex(SoundsTable._ID))));
			s.setTitle(c.getString(c.getColumnIndex(SoundsTable.COLUMN_TITLE)));
			s.setUri(Uri.parse(c.getString(c.getColumnIndex(SoundsTable.COLUMN_URI))));
			s.setSoundboardId(c.getString(c.getColumnIndex(SoundsTable.COLUMN_SOUNDBOARD_ID)));
		}
		c.close();
		return s;
	}
	
	private ArrayList<Sound>getSoundsForSoundboard(Soundboard sb){
		ArrayList<Sound> sounds = new ArrayList<Sound>();
		c = sbDAO.read(this.getReadableDatabase(), SoundsTable.TABLE_NAME,null, SoundsTable.COLUMN_SOUNDBOARD_ID+" =?",new String[]{sb.getID()+""},null);
		while(c.moveToNext()){
			Sound s = new Sound();
			s.setID(Integer.parseInt(c.getString(c.getColumnIndex(SoundsTable._ID))));
			s.setTitle(c.getString(c.getColumnIndex(SoundsTable.COLUMN_TITLE)));
			s.setUri(Uri.parse(c.getString(c.getColumnIndex(SoundsTable.COLUMN_URI))));
			s.setSoundboardId(sb.getID()+"");
			sounds.add(s);
		}
		c.close();
		return sounds;
	}
	
	public int getCountOfSoundboards(){
		c  = sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME,null,null,null,null);
		int count = c.getCount();
		c.close();
		return count;
	}
	
	public ArrayList<String> getSoundboardNames(){
		c = sbDAO.read(this.getReadableDatabase(), SoundboardsTable.TABLE_NAME,new String[]{SoundboardsTable.COLUMN_NAME, SoundboardsTable._ID}, null, null,null);
		ArrayList<String> list = new ArrayList<String>();
		while(c.moveToNext()){
			list.add(c.getString(c.getColumnIndex(SoundboardsTable.COLUMN_NAME)));
		}
		return list;
	}
	
	public ArrayList<String> getSoundboardIds(){
		c =sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME,new String[]{SoundboardsTable._ID},null,null,null);
		ArrayList<String> list = new ArrayList<>();
		while (c.moveToNext()){
			list.add(c.getString(c.getColumnIndex(SoundboardsTable._ID)));
		}
		return list;
	}
	
	public void renameSoundboard(String newName, String soundboardid){
        ContentValues values = new ContentValues();
        values.put(SoundboardsTable.COLUMN_NAME, newName);
        sbDAO.update(getWritableDatabase(), values, SoundboardsTable.TABLE_NAME, SoundboardsTable._ID + "=?", new String[]{soundboardid});
    }
}
