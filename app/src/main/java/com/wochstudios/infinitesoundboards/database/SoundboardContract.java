package com.wochstudios.infinitesoundboards.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class SoundboardContract
{
    public static final String CONTENT_AUTHORITY = "com.wochstudios.infinitesoundboards";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SOUNDBOARDS = "sounboards";
    public static final String PATH_SOUNDS = "sounds";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static abstract class SoundboardsTable implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SOUNDBOARDS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOUNDBOARDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOUNDBOARDS;

        public static final String TABLE_NAME = "soundboards";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE_CREATED = "date_created";

        public static final String SQL_CREATE_TABLE_SOUNDBOARDS =
                "CREATE TABLE " + SoundboardsTable.TABLE_NAME + " (" +
                        SoundboardsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SoundboardsTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        SoundboardsTable.COLUMN_DATE_CREATED + TEXT_TYPE +
                        ")";
        public static final String SQL_DELETE_TABLE_SOUNDBOARDS =
                "DROP TABLE IF EXISTS " + SoundboardsTable.TABLE_NAME;

        public static Uri buildSounboardUri(long soundboardId) {
            return ContentUris.withAppendedId(CONTENT_URI, soundboardId);
        }

    }

    public static abstract class SoundsTable implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SOUNDS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOUNDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOUNDS;

        public static final String TABLE_NAME = "sounds";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_SOUNDBOARD_ID = "soundboard_id";

        public static final String SQL_CREATE_TABLE_SOUNDS =
                "CREATE TABLE " + SoundsTable.TABLE_NAME + " (" +
                        SoundsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SoundsTable.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                        SoundsTable.COLUMN_URI + TEXT_TYPE + COMMA_SEP +
                        SoundsTable.COLUMN_SOUNDBOARD_ID + " INTEGER, " +
                        "FOREIGN KEY(" + SoundsTable.COLUMN_SOUNDBOARD_ID +
                        ") REFERENCES " + SoundboardsTable.TABLE_NAME + "(" + SoundboardsTable._ID + ")" +
                        ")";
        public static final String SQL_DELETE_TABLE_SOUNDS =
                "DROP TABLE IF EXISTS " + SoundsTable.TABLE_NAME;


        public static Uri buildSoundUri(long soundId) {
            return ContentUris.withAppendedId(CONTENT_URI, soundId);
        }

        public static Uri buildSoundsFromSoundboardUri(String soundboardId) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_SOUNDBOARD_ID, soundboardId).build();
        }

        public static String getSoundboardIdFromUri(Uri uri) {
            String id = uri.getQueryParameter(COLUMN_SOUNDBOARD_ID);
            if (!id.isEmpty() && id != null) {
                return id;
            }
            return "";
        }
    }
}