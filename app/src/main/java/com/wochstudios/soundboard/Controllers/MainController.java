package com.wochstudios.soundboard.Controllers;

import android.content.Context;
import android.net.Uri;

import com.wochstudios.soundboard.utils.MapLoader;
import com.wochstudios.soundboard.utils.RingtoneMaker;
import com.wochstudios.soundboard.utils.SoundPlayer;

import java.util.HashMap;


public class MainController {
	private SoundPlayer player = new SoundPlayer();
	private RingtoneMaker ringtoneMaker = new RingtoneMaker();
	private HashMap<String,String> valueMap;
	private Context con;
	
	
	public MainController(Context c, HashMap<String,String> map){
		this.con = c;
		this.valueMap = map;
	}
	
	public void playSound(String key){
		player.playSound(con, Uri.parse(valueMap.get(key)));
	}
	
	
	public void downloadRingtone(String key){
		ringtoneMaker.setRingtone(key, con);
	}
	
	
	
	
	

}
