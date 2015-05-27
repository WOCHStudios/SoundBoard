package com.wochstudios.soundboard;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.net.Uri;

import com.wochstudios.soundboard.utils.Downloader;
import com.wochstudios.soundboard.utils.MapLoader;
import com.wochstudios.soundboard.utils.SoundPlayer;
import com.wochstudios.soundboard.utils.*;


public class SoundBoardController {
	private SoundPlayer player = new SoundPlayer();
	private MapLoader loader = new MapLoader();
	private Downloader downloader = new Downloader();
	private RingtoneMaker ringtoneMaker = new RingtoneMaker();
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
			InputStream f = con.openFileInput("filemap.txt");
			return loader.loadVaules(f);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String,String>();
		}
	}
	
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
