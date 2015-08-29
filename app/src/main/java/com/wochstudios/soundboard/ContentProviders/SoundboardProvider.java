package com.wochstudios.soundboard.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.wochstudios.soundboard.Database.SounboardContract;

/**
 * Created by dave on 8/6/2015.
 */
public class SoundboardProvider extends ContentProvider {

    static final int SOUNDBOARD = 100;
    static final int SOUNDS = 200;
    static final int SOUNDS_FROM_SOUNBOARD = 201;
    private UriMatcher matcher = buildMatcher();

    private static UriMatcher buildMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SounboardContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SounboardContract.PATH_SOUNDBOARDS, SOUNDBOARD);
        matcher.addURI(authority, SounboardContract.PATH_SOUNDS, SOUNDS);
        matcher.addURI(authority, SounboardContract.PATH_SOUNDBOARDS + "/#/sounds", SOUNDS_FROM_SOUNBOARD);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
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