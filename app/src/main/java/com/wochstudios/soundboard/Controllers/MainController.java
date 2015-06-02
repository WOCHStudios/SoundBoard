package com.wochstudios.soundboard.Controllers;

import android.content.Context;
import android.net.Uri;

import com.wochstudios.soundboard.utils.MapLoader;
import com.wochstudios.soundboard.utils.RingtoneMaker;
import com.wochstudios.soundboard.utils.SoundPlayer;

import java.util.HashMap;


public class MainController {
	private SoundPlayer player = new SoundPlayer();
	private MapLoader loader = new MapLoader();
	private RingtoneMaker ringtoneMaker = new RingtoneMaker();
	private HashMap<String,String> valueMap;
	private Context con;
	
	
	public MainController(Context c, HashMap<String,String> map){
		this.con = c;
		this.valueMap = map;
	}
	/*
	public Set<String> getMapKeys(){
		valueMap = this.loadSounds();
		return valueMap.keySet();
	}
	
	public HashMap<String,String> loadSounds(){
		try {
			InputStream f = con.openFileInput("filemap.txt");
			return loader.loadVaules(f);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String,String>();
		}
	}*/
	
	public void playSound(String key){
		//int rawID = con.getResources().getIdentifier(valueMap.get(key), "raw", con.getPackageName());
		//player.playSound(con, rawID);
		player.playSound(con, Uri.parse(valueMap.get(key)));
		
	}
	
	
	public void downloadRingtone(String key){
		//int rawID = con.getResources().getIdentifier(valueMap.get(key), "raw", con.getPackageName());
		//downloader.downloadRingtone(con,con.getResources().openRawResource(rawID) , key);
		ringtoneMaker.setRingtone(key, con);
	}
	
	
	
	
	

}
