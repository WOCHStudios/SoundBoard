package com.wochstudios.soundboard.Controllers;

import android.content.Context;
import android.net.Uri;

import com.wochstudios.soundboard.utils.RingtoneMaker;
import com.wochstudios.soundboard.utils.SoundPlayer;

import java.util.HashMap;
import com.wochstudios.soundboard.Models.*;


public class MainController {
	private SoundPlayer player = new SoundPlayer();
	private RingtoneMaker ringtoneMaker = new RingtoneMaker();
	private HashMap<String,String> valueMap;
	private Context con;
	private Soundboard soundboard;
	
	
	public MainController(Context c, HashMap<String,String> map){
		this.con = c;
		this.valueMap = map;
	}
	
	public MainController(Context c, Soundboard sb){
		this.con = c;
		this.soundboard = sb;
	}
	
	public void playSound(String key){
		for(Sound temp : soundboard.getSounds()){
			if(key.equals(temp.getTitle())){
				player.playSound(con, temp.getUri());
			}
		}
		//player.playSound(con, Uri.parse(valueMap.get(key)));
	}
	
	public void setSoundboard(Soundboard sb){
		this.soundboard = sb;
	}
	
	public void downloadRingtone(String key){
		ringtoneMaker.setRingtone(key, con);
	}
	
	
	
	
	

}
