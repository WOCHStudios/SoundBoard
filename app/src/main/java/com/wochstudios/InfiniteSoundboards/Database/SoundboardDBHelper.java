
package com.wochstudios.InfiniteSoundboards.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.wochstudios.InfiniteSoundboards.Database.DAO.ISoundboardDAO;
import com.wochstudios.InfiniteSoundboards.Database.DAO.SoundboardDAO;
import com.wochstudios.InfiniteSoundboards.Database.SounboardContract.SoundboardsTable;
import com.wochstudios.InfiniteSoundboards.Database.SounboardContract.SoundsTable;
import com.wochstudios.InfiniteSoundboards.Models.Sound;
import com.wochstudios.InfiniteSoundboards.Models.Soundboard;

import java.util.ArrayList;

public class SoundboardDBHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Soundboard.db";
	
	
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
        boolean result = sbDAO.insert(this.getWritableDatabase(), table, values);
        Log.i(getClass().getSimpleName(), result+"");
    }
	
	public void removeFromDatabase(String table,String id){
		sbDAO.delete(this.getWritableDatabase(), table, BaseColumns._ID + " =?", new String[]{id});
	}

    public ArrayList<Soundboard> getAllSoundboards(){
        ArrayList<Soundboard> soundboards = new ArrayList<Soundboard>();
        Cursor cursor = sbDAO.read(this.getReadableDatabase(), SoundboardsTable.TABLE_NAME,new String[]{SoundboardsTable._ID, SoundboardsTable.COLUMN_NAME,SoundboardsTable.COLUMN_DATE_CREATED},null,null,null);
        while(cursor.moveToNext()){
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SoundboardsTable._ID)));
            String title = cursor.getString(cursor.getColumnIndex(SoundboardsTable.COLUMN_NAME));
            String date =  cursor.getString(cursor.getColumnIndex(SoundboardsTable.COLUMN_DATE_CREATED));
            Soundboard temp = new Soundboard(id,title,date);
            getSoundsForSoundboard(temp);
            soundboards.add(temp);
        }
        cursor.close();
        return soundboards;
    }
	

	public Soundboard findSoundboard(String id){
		Soundboard sb = new Soundboard();
		Cursor cursor = sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME, null, SoundboardsTable._ID+" = ?", new String[]{id}, null);
		while(cursor.moveToNext()){
			sb.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SoundboardsTable._ID))));
			sb.setTitle(cursor.getString(cursor.getColumnIndex(SoundboardsTable.COLUMN_NAME)));
			sb.setDate_created(cursor.getString(cursor.getColumnIndex(SoundboardsTable.COLUMN_DATE_CREATED)));
		}
		cursor.close();
		sb.setSounds(getSoundsForSoundboard(sb));
		return sb;
	}

	
	public Sound findSound(){
		Sound s = new Sound();
		Cursor cursor = sbDAO.read(this.getReadableDatabase(), SoundsTable.TABLE_NAME,null,SoundsTable._ID+" =?", new String[]{"1"},null);
		while(cursor.moveToNext()){
			s.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SoundsTable._ID))));
			s.setTitle(cursor.getString(cursor.getColumnIndex(SoundsTable.COLUMN_TITLE)));
			s.setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(SoundsTable.COLUMN_URI))));
			s.setSoundboardId(cursor.getString(cursor.getColumnIndex(SoundsTable.COLUMN_SOUNDBOARD_ID)));
		}
		cursor.close();
		return s;
	}
	
	private ArrayList<Sound>getSoundsForSoundboard(Soundboard sb){
		ArrayList<Sound> sounds = new ArrayList<Sound>();
		Cursor cursor = sbDAO.read(this.getReadableDatabase(), SoundsTable.TABLE_NAME,null, SoundsTable.COLUMN_SOUNDBOARD_ID+" =?",new String[]{sb.getID()+""},null);
		while(cursor.moveToNext()){
			Sound s = new Sound();
			s.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SoundsTable._ID))));
			s.setTitle(cursor.getString(cursor.getColumnIndex(SoundsTable.COLUMN_TITLE)));
			s.setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(SoundsTable.COLUMN_URI))));
			s.setSoundboardId(sb.getID()+"");
			sounds.add(s);
		}
		cursor.close();
		return sounds;
	}
	
	public int getCountOfSoundboards(){
		Cursor cursor  = sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME,null,null,null,null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	public ArrayList<String> getSoundboardNames(){
		Cursor cursor = sbDAO.read(this.getReadableDatabase(), SoundboardsTable.TABLE_NAME,new String[]{SoundboardsTable.COLUMN_NAME, SoundboardsTable._ID}, null, null,null);
		ArrayList<String> list = new ArrayList<String>();
		while(cursor.moveToNext()){
			list.add(cursor.getString(cursor.getColumnIndex(SoundboardsTable.COLUMN_NAME)));
		}
		return list;
	}
	
	public ArrayList<String> getSoundboardIds(){
		Cursor cursor =sbDAO.read(this.getReadableDatabase(),SoundboardsTable.TABLE_NAME,new String[]{SoundboardsTable._ID},null,null,null);
		ArrayList<String> list = new ArrayList<>();
		while (cursor.moveToNext()){
			list.add(cursor.getString(cursor.getColumnIndex(SoundboardsTable._ID)));
		}
		return list;
	}
	
	public void renameSoundboard(String newName, String soundboardid){
        ContentValues values = new ContentValues();
        values.put(SoundboardsTable.COLUMN_NAME, newName);
        sbDAO.update(getWritableDatabase(), values, SoundboardsTable.TABLE_NAME, SoundboardsTable._ID + "=?", new String[]{soundboardid});
    }
}
