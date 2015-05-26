package com.wochstudios.soundboard;

import android.net.Uri;
import android.content.Context;
import android.content.ContentResolver;

import com.wochstudios.soundboard.utils.MapUpdater;

public class AddSoundController
{
	private Context con;
	public AddSoundController(Context c){
		this.con = c;
	}
	
	public void AddSoundToFile(String title, String path){
		MapUpdater mu = new MapUpdater();
		mu.updateSoundMapFile(title, path, con);
	}
	
}
