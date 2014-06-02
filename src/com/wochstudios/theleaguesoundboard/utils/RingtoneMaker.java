package com.wochstudios.theleaguesoundboard.utils;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.wochstudios.theleaguesoundboard.R;

public class RingtoneMaker {
	public void setRingtone(String name, Context con){
		File k = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"download"+File.separator+name+".mp3");
		ContentValues values = new ContentValues();
		   values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
		   values.put(MediaStore.MediaColumns.TITLE, name);
		   values.put(MediaStore.MediaColumns.SIZE, k.length());
		   values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
		   values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
		   values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		   values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		   values.put(MediaStore.Audio.Media.IS_ALARM, true);
		   values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		   //Insert it into the database
		   Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
		   con.getContentResolver().insert(uri, values);
	}
}
