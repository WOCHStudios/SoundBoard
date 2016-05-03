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

import com.wochstudios.infinitesoundboards.contentprovider.SoundboardProvider;
import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.database.SoundboardContentDBHelper;

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

            assertEquals("Error: SoundboardProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + SoundboardContract.CONTENT_AUTHORITY,
                    providerInfo.authority, SoundboardContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: SoundboardProvider not registered at " + mContext.getPackageName()+"ComponentName: "+componentName.toString(),
                    false);
        }
    }



    public void testInsert(){
        Uri soundInsertUri = mContext.getContentResolver().insert(SoundboardContract.SoundsTable.CONTENT_URI, soundValues);
        assertEquals("Error: " + soundInsertUri.toString() + " does not match " + SoundboardContract.SoundsTable.CONTENT_TYPE,
                SoundboardContract.SoundsTable.CONTENT_TYPE,
                mContext.getContentResolver().getType(soundInsertUri));

        Uri soundBoardInsertUri = mContext.getContentResolver().insert(SoundboardContract.SoundboardsTable.CONTENT_URI, soundboardValues);
        assertEquals("Error: "+soundInsertUri.toString()+" does not match "+ SoundboardContract.SoundboardsTable.CONTENT_TYPE,
                SoundboardContract.SoundboardsTable.CONTENT_TYPE,
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
        soundValues.put(SoundboardContract.SoundsTable.COLUMN_TITLE, "TestUpdate");
        int soundResult = mContext.getContentResolver().update(
                SoundboardContract.SoundsTable.CONTENT_URI,
                soundValues,
                SoundboardContract.SoundsTable.COLUMN_TITLE + "=?",
                new String[]{"FromMethod"}
        );
        assertEquals("Error Updating sound were _ID = 2", 1, soundResult);

        Log.d(this.getClass().getSimpleName(), "Sounds Size @ testUpdate:" + querySoundboard().getCount());
        soundboardValues.put(SoundboardContract.SoundboardsTable.COLUMN_NAME, "TestUpdateSoundboard");
        int sounboardResult = mContext.getContentResolver().update(
                SoundboardContract.SoundboardsTable.CONTENT_URI,
                soundboardValues,
                SoundboardContract.SoundboardsTable.COLUMN_NAME +"=?",
                new String[]{"FromMethod"}
        );
        assertEquals("Error Updating infinitesoundboards where _ID =2", 1, sounboardResult);

    }

    public void testDelete(){
        insertTestRow();
        int soundResult = mContext.getContentResolver().delete(
                SoundboardContract.SoundsTable.CONTENT_URI,
                null,
                null
        );
        assertEquals("Error deleting rows from Sounds", 1, soundResult);

        int soundBoardResult = mContext.getContentResolver().delete(
                SoundboardContract.SoundboardsTable.CONTENT_URI,
                null,
                null
        );
        assertEquals("Error deleting rows from Soundoards",1,soundBoardResult);
    }

    private void insertTestRow(){
        SoundboardContentDBHelper dbHelper = new SoundboardContentDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues sbValues = new ContentValues();
        sbValues.put(SoundboardContract.SoundboardsTable.COLUMN_NAME, "FromMethod");
        sbValues.put(SoundboardContract.SoundboardsTable.COLUMN_DATE_CREATED, "8/9/2105");
        db.insert(SoundboardContract.SoundboardsTable.TABLE_NAME, null, sbValues);


        ContentValues sValues = new ContentValues();
        sValues.put(SoundboardContract.SoundsTable.COLUMN_TITLE,"FromMethod");
        sValues.put(SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID,"25");
        sValues.put(SoundboardContract.SoundsTable.COLUMN_URI,"Content");
        db.insert(SoundboardContract.SoundsTable.TABLE_NAME, null, sValues);
        db.close();
    }

    private void deleteAllRecords(){
        SoundboardContentDBHelper dbHelper = new SoundboardContentDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(SoundboardContract.SoundboardsTable.TABLE_NAME, null, null);
        db.delete(SoundboardContract.SoundsTable.TABLE_NAME, null, null);
        db.close();
    }

    private void setupContentValues(){
        soundboardValues = new ContentValues();
        soundboardValues.put(SoundboardContract.SoundboardsTable.COLUMN_NAME,"TestSoundBoard");
        soundboardValues.put(SoundboardContract.SoundboardsTable.COLUMN_DATE_CREATED,"8/31/2015");

        soundValues = new ContentValues();
        soundValues.put(SoundboardContract.SoundsTable.COLUMN_TITLE, "TestSound");
        soundValues.put(SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID, "1");
        soundValues.put(SoundboardContract.SoundsTable.COLUMN_URI, "TestURI");
    }

    private Cursor querySoundboard(){
        return mContext.getContentResolver().query(
                SoundboardContract.SoundboardsTable.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    private Cursor querySounds(){
        return mContext.getContentResolver().query(
                SoundboardContract.SoundsTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }
}
