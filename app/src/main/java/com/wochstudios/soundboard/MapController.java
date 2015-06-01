package com.wochstudios.soundboard;

import android.net.Uri;
import android.content.Context;
import android.content.ContentResolver;

import com.wochstudios.soundboard.utils.MapUpdater;
import com.wochstudios.soundboard.utils.*;

public class MapController
{
	private Context con;
	private MapUpdater mu;
	private MapCreator mc;
	
	public MapController(Context c){
		this.con = c;
		mu = new MapUpdater(con);
		mc = new MapCreator();
	}
	
	public void createMap(){
		mc.createMapFile(con);
	}
	
	public void AddSoundToMap(String title, String Uri){
		mu.updateSoundMap(MapUpdater.ADD_CODE, title, Uri);
	}
	
	public void RemoveSoundFromMap(String title, String Uri){
		mu.updateSoundMap(MapUpdater.REMOVE_CODE, title, Uri);
	}
	
}
