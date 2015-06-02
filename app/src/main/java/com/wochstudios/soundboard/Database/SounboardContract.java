package com.wochstudios.soundboard.Database;

import android.provider.BaseColumns;

public final class SounboardContract
{
	private static final String TEXT_TYPE = "TEXT";
	private static final String COMMA_SEP = ",";
	
	public SounboardContract(){}
	
	public static abstract class SoundboardsTable implements BaseColumns{
		public static final String TABLE_NAME = "soundboards";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_DATE_CREATED = "date_created";
		public static final String SQL_CREATE_TABLE_SOUNDBOARDS = 
			"CREATE TABLE" + SoundboardsTable.TABLE_NAME + " ("+
			SoundboardsTable._ID + " INTEGER PRIMARY KEY," +
			SoundboardsTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			SoundboardsTable.COLUMN_DATE_CREATED + TEXT_TYPE + COMMA_SEP+
			")";
		public static final String SQL_DELETE_TABLE_SOUNDBOARDS = 
			"DROP TABLE IF EXISTS "+SoundboardsTable.TABLE_NAME;
	}
	
	public static abstract class SoundsTable implements BaseColumns{
		public static final String TABLE_NAME = "sounds";
		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_URI = "uri";
		public static final String COLUMN_SOUNDBOARD_ID = "soundboard_id";
		public static final String SQL_CREATE_TABLE_SOUNDS = 
			"CREATE TABLE" + SoundsTable.TABLE_NAME + " ("+
			SoundsTable._ID + " INTEGER PRIMARY KEY," +
			SoundsTable.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
			SoundsTable.COLUMN_URI + TEXT_TYPE + COMMA_SEP+
			SoundsTable.COLUMN_SOUNDBOARD_ID + "INTEGER FOREIGN KEY REFERENCES soundboards(_ID)"+
			")";
		public static final String SQL_DELETE_TABLE_SOUNDS = 
			"DROP TABLE IF EXISTS "+SoundsTable.TABLE_NAME;		
	}
}
