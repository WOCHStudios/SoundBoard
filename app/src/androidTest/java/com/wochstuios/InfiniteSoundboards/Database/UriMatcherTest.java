package com.wochstuios.InfiniteSoundboards.Database;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.wochstudios.InfiniteSoundboards.contentprovider.SoundboardProvider;
import com.wochstudios.InfiniteSoundboards.database.SoundboardContract;


/**
 * Created by dave on 8/29/2015.
 */
public class UriMatcherTest extends AndroidTestCase {
    private static final long SOUNDBOARD_ID = 1;
    private static final long SOUND_ID = 1;

    private static final Uri TEST_SOUNDBOARD_URI = SoundboardContract.SoundboardsTable.CONTENT_URI;
    private static final Uri TEST_SOUNDBOARD_WITH_ID_URI = SoundboardContract.SoundboardsTable.buildSounboardUri(SOUNDBOARD_ID);
    private static final Uri TEST_SOUND_URI = SoundboardContract.SoundsTable.CONTENT_URI;
    private static final Uri TEST_SOUND_WITH_ID_URI = SoundboardContract.SoundsTable.buildSoundUri(SOUND_ID);
    private static final Uri TEST_SOUNDS_FOR_SOUNDBOARD_URI = SoundboardContract.SoundsTable.buildSoundsFromSoundboardUri(SOUNDBOARD_ID + "");


    public UriMatcherTest(){
        super();
    }


    public void testUriMatcher() throws Throwable{
        UriMatcher testMatcher = SoundboardProvider.buildMatcher();

        assertEquals("Error: Sound URI matched incorrectly", testMatcher.match(TEST_SOUND_URI), SoundboardProvider.SOUNDS);
        assertEquals("Error: Soundboard URI matched incorrectly", testMatcher.match(TEST_SOUNDBOARD_URI), SoundboardProvider.SOUNDBOARD);
        assertEquals("Error: Soundboard with id URI matched incorrectly", testMatcher.match(TEST_SOUNDBOARD_WITH_ID_URI), SoundboardProvider.SOUNDBOARD_WITH_ID);
        assertEquals("Error: Sound with id URI matched incorrectly", testMatcher.match(TEST_SOUND_WITH_ID_URI), SoundboardProvider.SOUNDS_WITH_ID);


    }
}
