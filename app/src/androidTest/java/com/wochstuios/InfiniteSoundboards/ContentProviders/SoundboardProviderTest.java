package com.wochstuios.InfiniteSoundboards.ContentProviders;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.wochstudios.InfiniteSoundboards.ContentProviders.SoundboardProvider;
import com.wochstudios.InfiniteSoundboards.Database.SounboardContract;
import com.wochstudios.InfiniteSoundboards.Database.SoundboardContentDBHelper;

/**
 * Created by dave on 8/29/2015.
 */
public class SoundboardProviderTest extends AndroidTestCase {
    private ContentValues soundboardValues;
    private ContentValues soundValues;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setupContentValues();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                SoundboardProvider.class.getName());
        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + SounboardContract.CONTENT_AUTHORITY,
                    providerInfo.authority, SounboardContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName()+"ComponentName: "+componentName.toString(),
                    false);
        }
    }



    public void testInsert(){
        Uri soundInsertUri = mContext.getContentResolver().insert(SounboardContract.SoundsTable.CONTENT_URI, soundValues);
        assertEquals("Error: " + soundInsertUri.toString() + " does not match " + SounboardContract.SoundsTable.CONTENT_TYPE,
                SounboardContract.SoundsTable.CONTENT_TYPE,
                mContext.getContentResolver().getType(soundInsertUri));

        Uri soundBoardInsertUri = mContext.getContentResolver().insert(SounboardContract.SoundboardsTable.CONTENT_URI, soundboardValues);
        assertEquals("Error: "+soundInsertUri.toString()+" does not match "+ SounboardContract.SoundboardsTable.CONTENT_TYPE,
                SounboardContract.SoundboardsTable.CONTENT_TYPE,
                mContext.getContentResolver().getType(soundBoardInsertUri));

    }

    public void testQuery(){
        insertTestRow();
        Cursor soundboardCursor = querySoundboard();
        assertEquals("Error querying Soundboards table",1,soundboardCursor.getCount());
        soundboardCursor.close();

        Cursor soundCursor = querySounds();
        assertEquals("Error querying Soundboards table", 1, soundCursor.getCount());
        soundCursor.close();
    }

    public void testUpdate(){
        insertTestRow();
        Log.d(this.getClass().getSimpleName(),"Sounds Size @ testUpdate:"+querySounds().getCount());
        soundValues.put(SounboardContract.SoundsTable.COLUMN_TITLE, "TestUpdate");
        int soundResult = mContext.getContentResolver().update(
                SounboardContract.SoundsTable.CONTENT_URI,
                soundValues,
                SounboardContract.SoundsTable.COLUMN_TITLE + "=?",
                new String[]{"FromMethod"}
        );
        assertEquals("Error Updating sound were _ID = 2", 1, soundResult);

        Log.d(this.getClass().getSimpleName(), "Sounds Size @ testUpdate:" + querySoundboard().getCount());
        soundboardValues.put(SounboardContract.SoundboardsTable.COLUMN_NAME, "TestUpdateSoundboard");
        int sounboardResult = mContext.getContentResolver().update(
                SounboardContract.SoundboardsTable.CONTENT_URI,
                soundboardValues,
                SounboardContract.SoundboardsTable.COLUMN_NAME +"=?",
                new String[]{"FromMethod"}
        );
        assertEquals("Error Updating InfiniteSoundboards where _ID =2", 1, sounboardResult);

    }

    public void testDelete(){
        insertTestRow();
        int soundResult = mContext.getContentResolver().delete(
                SounboardContract.SoundsTable.CONTENT_URI,
                null,
                null
        );
        assertEquals("Error deleting rows from Sounds", 1, soundResult);

        int soundBoardResult = mContext.getContentResolver().delete(
                SounboardContract.SoundboardsTable.CONTENT_URI,
                null,
                null
        );
        assertEquals("Error deleting rows from Soundoards",1,soundBoardResult);
    }

    private void insertTestRow(){
        SoundboardContentDBHelper dbHelper = new SoundboardContentDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues sbValues = new ContentValues();
        sbValues.put(SounboardContract.SoundboardsTable.COLUMN_NAME, "FromMethod");
        sbValues.put(SounboardContract.SoundboardsTable.COLUMN_DATE_CREATED, "8/9/2105");
        db.insert(SounboardContract.SoundboardsTable.TABLE_NAME, null, sbValues);


        ContentValues sValues = new ContentValues();
        sValues.put(SounboardContract.SoundsTable.COLUMN_TITLE,"FromMethod");
        sValues.put(SounboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID,"25");
        sValues.put(SounboardContract.SoundsTable.COLUMN_URI,"Content");
        db.insert(SounboardContract.SoundsTable.TABLE_NAME, null, sValues);
        db.close();
    }

    private void deleteAllRecords(){
        SoundboardContentDBHelper dbHelper = new SoundboardContentDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(SounboardContract.SoundboardsTable.TABLE_NAME, null, null);
        db.delete(SounboardContract.SoundsTable.TABLE_NAME, null, null);
        db.close();
    }

    private void setupContentValues(){
        soundboardValues = new ContentValues();
        soundboardValues.put(SounboardContract.SoundboardsTable.COLUMN_NAME,"TestSoundBoard");
        soundboardValues.put(SounboardContract.SoundboardsTable.COLUMN_DATE_CREATED,"8/31/2015");

        soundValues = new ContentValues();
        soundValues.put(SounboardContract.SoundsTable.COLUMN_TITLE, "TestSound");
        soundValues.put(SounboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID, "1");
        soundValues.put(SounboardContract.SoundsTable.COLUMN_URI, "TestURI");
    }

    private Cursor querySoundboard(){
        return mContext.getContentResolver().query(
                SounboardContract.SoundboardsTable.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    private Cursor querySounds(){
        return mContext.getContentResolver().query(
                SounboardContract.SoundsTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }
}
