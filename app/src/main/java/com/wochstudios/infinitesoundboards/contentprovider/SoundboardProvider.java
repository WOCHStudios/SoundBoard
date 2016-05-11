package com.wochstudios.infinitesoundboards.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.database.SoundboardContentDBHelper;

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
        final String authority = SoundboardContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SoundboardContract.PATH_SOUNDBOARDS, SOUNDBOARD);
        matcher.addURI(authority, SoundboardContract.PATH_SOUNDBOARDS + "/#", SOUNDBOARD_WITH_ID);
        matcher.addURI(authority, SoundboardContract.PATH_SOUNDS, SOUNDS);
        matcher.addURI(authority, SoundboardContract.PATH_SOUNDS + "/#", SOUNDS_WITH_ID);

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
            case SOUNDS :
            case SOUNDS_WITH_ID:
                return SoundboardContract.SoundsTable.CONTENT_TYPE;
            case SOUNDBOARD:
            case SOUNDBOARD_WITH_ID:
                return SoundboardContract.SoundboardsTable.CONTENT_TYPE;
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
                        SoundboardContract.SoundboardsTable.TABLE_NAME,
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
                            SoundboardContract.SoundsTable.TABLE_NAME,
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
        String selection = SoundboardContract.SoundsTable._ID + "=?";
        String[] args = {sound_id + ""};

        return database.getReadableDatabase().query(
                SoundboardContract.SoundsTable.TABLE_NAME,
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
        String selection = SoundboardContract.SoundboardsTable._ID + "=?";
        String[] args = {soundboard_id};
        return database.getReadableDatabase().query(
                SoundboardContract.SoundboardsTable.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                sortOrder
        );
    }

    private Cursor findSoundsBySoundboardId(Uri uri, String[] projection, String sortOrder) {
        String soundboard_id = uri.getQueryParameter(SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID);
        String selection = SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID + "=?";
        String[] args = {soundboard_id};
        return database.getReadableDatabase().query(
                SoundboardContract.SoundsTable.TABLE_NAME,
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
        Uri returnUri;
        switch (matcher.match(uri)) {
            case SOUNDBOARD:
                returnUri = insertSoundboard(uri, values);
                break;
            case SOUNDS:
                returnUri = insertSound(uri, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    private Uri insertSoundboard(Uri uri, ContentValues values) {
        long _id = database.getWritableDatabase().insert(
                SoundboardContract.SoundboardsTable.TABLE_NAME,
                null,
                values
                );
        if(_id > 0){
            return SoundboardContract.SoundboardsTable.buildSounboardUri(_id);
        }else{
            throw new android.database.SQLException("Failed to insert into" + uri);
        }
    }

    private Uri insertSound(Uri uri, ContentValues values) {
        long _id = database.getWritableDatabase().insert(
                SoundboardContract.SoundsTable.TABLE_NAME,
                null,
                values
        );
        if(_id > 0){
            return SoundboardContract.SoundsTable.buildSoundUri(_id);
        }else{
            throw new android.database.SQLException("Failed to insert into" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;
        if(selection == null) selection = "1";
        Log.d(this.getClass().getSimpleName(),"Matcher result: "+matcher.match(uri));
        switch (matcher.match(uri)) {
            case SOUNDBOARD:
                rowsDeleted = database.getWritableDatabase().delete(
                        SoundboardContract.SoundboardsTable.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case SOUNDS:
                rowsDeleted = database.getWritableDatabase().delete(
                        SoundboardContract.SoundsTable.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated;
        Log.d(this.getClass().getSimpleName(),"Matcher result: "+matcher.match(uri));
        switch (matcher.match(uri)) {
            case SOUNDBOARD:
                rowsUpdated = database.getWritableDatabase().update(
                        SoundboardContract.SoundboardsTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            case SOUNDS:
                rowsUpdated = database.getWritableDatabase().update(
                        SoundboardContract.SoundsTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


}