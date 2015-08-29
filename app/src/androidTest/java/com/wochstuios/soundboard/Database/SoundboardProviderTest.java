package com.wochstuios.soundboard.Database;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.test.AndroidTestCase;

import com.wochstudios.soundboard.ContentProviders.SoundboardProvider;
import com.wochstudios.soundboard.Database.SounboardContract;

/**
 * Created by dave on 8/29/2015.
 */
public class SoundboardProviderTest extends AndroidTestCase {

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(), SoundboardProvider.class.getName());
        try {
            ProviderInfo info = pm.getProviderInfo(componentName, 0);
            assertEquals("Error: WeatherProvider registered with authority: " + info.authority +
                            " instead of authority: " + SounboardContract.CONTENT_AUTHORITY,
                    info.authority, SounboardContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }
}
