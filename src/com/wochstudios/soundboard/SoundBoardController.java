package com.wochstudios.soundboard;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.wochstudios.soundboard.utils.Downloader;
import com.wochstudios.soundboard.utils.MapLoader;
import com.wochstudios.soundboard.utils.SoundPlayer;


public class SoundBoardController {
	private SoundPlayer player = new SoundPlayer();
	private MapLoader loader = new MapLoader();
	private Downloader downloader = new Downloader();
	private HashMap<String,String> valueMap;
	private Context con;
	
	
	public SoundBoardController(Context c){
		this.con = c;
		valueMap = this.loadSounds();
	}
	
	public Set<String> getMapKeys(){
		return valueMap.keySet();
	}
	
	public HashMap<String,String> loadSounds(){
		try {
			int id = con.getResources().getIdentifier("filemap", "raw", con.getPackageName());
			InputStream f = con.getResources().openRawResource(id);
			return loader.loadVaules(f);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String,String>();
		}
	}
	
	public void playSound(String key){
		Log.i("MainController:playSound()",""+valueMap.get(key));
		int rawID = con.getResources().getIdentifier(valueMap.get(key), "raw", con.getPackageName());
		player.playSound(con, rawID);
	}
	
	
	public void downloadRingtone(String key){
		int rawID = con.getResources().getIdentifier(valueMap.get(key), "raw", con.getPackageName());
		downloader.downloadRingtone(con,con.getResources().openRawResource(rawID) , key);
	}
	
	
	
	
	

}
