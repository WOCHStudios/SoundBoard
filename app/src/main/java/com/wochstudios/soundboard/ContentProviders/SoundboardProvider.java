package com.wochstudios.soundboard.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.wochstudios.soundboard.Database.SounboardContract;
import com.wochstudios.soundboard.Database.SoundboardContentDBHelper;

/**
 * Created by dave on 8/6/2015.
 */
public class SoundboardProvider extends ContentProvider {

    public static final int SOUNDBOARD = 100;
    public static final int SOUNDBOARD_WITH_ID = 101;
    public static final int SOUNDS = 200;
    public static final int SOUNDS_WITH_ID = 201;

    private UriMatcher matcher = buildMatcher();

    private SoundboardContentDBHelper database;


    public static UriMatcher buildMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SounboardContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SounboardContract.PATH_SOUNDBOARDS, SOUNDBOARD);
        matcher.addURI(authority, SounboardContract.PATH_SOUNDBOARDS + "/#", SOUNDBOARD_WITH_ID);
        matcher.addURI(authority, SounboardContract.PATH_SOUNDS, SOUNDS);
        matcher.addURI(authority, SounboardContract.PATH_SOUNDS + "/#", SOUNDS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        database = new SoundboardContentDBHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        int match = matcher.match(uri);
        switch (match) {
            case SOUNDS:
                return SounboardContract.SoundsTable.CONTENT_TYPE;
            case SOUNDBOARD:
                return SounboardContract.SoundboardsTable.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor results;
        switch (matcher.match(uri)) {
            case SOUNDBOARD:
                results = database.getReadableDatabase().query(
                        SounboardContract.SoundboardsTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SOUNDS:
                if (uri.getQueryParameterNames().isEmpty()) {
                    results = database.getReadableDatabase().query(
                            SounboardContract.SoundsTable.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                    );
                } else {
                    results = findSoundsBySoundboardId(uri, projection, sortOrder);
                }
                break;
            case SOUNDBOARD_WITH_ID:
                results = findSounboardById(uri, projection, sortOrder);
                break;
            case SOUNDS_WITH_ID:
                results = findSoundById(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        results.setNotificationUri(getContext().getContentResolver(), uri);
        return results;
    }


    private Cursor findSoundById(Uri uri, String[] projection, String sortOrder) {
        long sound_id = ContentUris.parseId(uri);
        String selection = SounboardContract.SoundsTable._ID + "=?";
        String[] args = {sound_id + ""};

        return database.getReadableDatabase().query(
                SounboardContract.SoundsTable.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                sortOrder
        );
    }

    private Cursor findSounboardById(Uri uri, String[] projection, String sortOrder) {
        String soundboard_id = ContentUris.parseId(uri) + "";
        String selection = SounboardContract.SoundboardsTable._ID + "=?";
        String[] args = {soundboard_id};
        return database.getReadableDatabase().query(
                SounboardContract.SoundboardsTable.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                sortOrder
        );
    }

    private Cursor findSoundsBySoundboardId(Uri uri, String[] projection, String sortOrder) {
        String soundboard_id = uri.getQueryParameter(SounboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID);
        String selection = SounboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID + "=?";
        String[] args = {soundboard_id};
        return database.getReadableDatabase().query(
                SounboardContract.SoundsTable.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                sortOrder
        );
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}