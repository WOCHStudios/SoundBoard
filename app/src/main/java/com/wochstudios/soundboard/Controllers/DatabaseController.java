package com.wochstudios.soundboard.Controllers;

import android.content.ContentValues;
import android.content.Context;

import com.wochstudios.soundboard.Database.SounboardContract.SoundboardsTable;
import com.wochstudios.soundboard.Database.SounboardContract.SoundsTable;
import com.wochstudios.soundboard.Database.SoundboardDBHelper;
import com.wochstudios.soundboard.Models.Soundboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseController
{
	private SoundboardDBHelper mDbHelper;
	
	public DatabaseController(Context con){
		mDbHelper = new  SoundboardDBHelper(con);
		//Add new soundboard for testing.
		//createTestSoundboard();
	}
	
	public void addSoundboardToDatabase(String name){
		ContentValues v = new ContentValues();
		v.put(SoundboardsTable.COLUMN_NAME,name);
		v.put(SoundboardsTable.COLUMN_DATE_CREATED,
					new SimpleDateFormat("mm/dd/yyyy").format(new Date()).toString());
		mDbHelper.insertIntoDatabase(SoundboardsTable.TABLE_NAME,v);
	}
	
	
	public void addSoundToSoundboard(String title, String uri, String sbid){
		ContentValues v = new ContentValues();
		v.put(SoundsTable.COLUMN_TITLE, title);
		v.put(SoundsTable.COLUMN_URI, uri);
		v.put(SoundsTable.COLUMN_SOUNDBOARD_ID, sbid);
		mDbHelper.insertIntoDatabase(SoundsTable.TABLE_NAME,v);
	}
	
	public void removeSoundFromSoundboard(String id){
		mDbHelper.removeFromDatabase(SoundsTable.TABLE_NAME,id);
	}
	
	public void removeSoundboardFromDatabase(String id){
		mDbHelper.removeFromDatabase(SoundboardsTable.TABLE_NAME,id);
	}
	
		
	public Soundboard getSoundboard(String id){
		Soundboard s = mDbHelper.findSoundboard(id);
		return s;
	}
	
	
	public boolean checkForSoundboards(){
		return mDbHelper.getCountOfSoundboards() > 0;
	}
	
	public ArrayList<String> getSoundboardNames(){
		ArrayList<String> temp = mDbHelper.getSoundboardNames();
		temp.add(0,"Add Soundboard..");
		return temp;
	}
	
	private void createTestSoundboard(){
		ContentValues sb = new ContentValues();
		sb.put(SoundboardsTable.COLUMN_NAME,"TestDatabase2");
		sb.put(SoundboardsTable.COLUMN_DATE_CREATED,
			  new SimpleDateFormat("mm/dd/yyyy").format(new Date()).toString());
			  
		ContentValues s = new ContentValues();
		s.put(SoundsTable.COLUMN_TITLE, "Test Sound");
		s.put(SoundsTable.COLUMN_URI, "TestUri");
		s.put(SoundsTable.COLUMN_SOUNDBOARD_ID, "2");	  
		
		
		mDbHelper.insertIntoDatabase(SoundboardsTable.TABLE_NAME, sb);
		mDbHelper.insertIntoDatabase(SoundsTable.TABLE_NAME, s);
		
	}
}
