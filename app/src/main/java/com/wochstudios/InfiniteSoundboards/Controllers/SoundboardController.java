package com.wochstudios.InfiniteSoundboards.Controllers;

import android.content.Context;

import com.wochstudios.InfiniteSoundboards.Models.Sound;
import com.wochstudios.InfiniteSoundboards.Models.Soundboard;
import com.wochstudios.InfiniteSoundboards.Utils.SoundPlayer;


public class SoundboardController {
	private SoundPlayer player = new SoundPlayer();
	//private RingtoneMaker ringtoneMaker = new RingtoneMaker();
	private Context con;
	private Soundboard soundboard;
	

	
	public SoundboardController(Context c, Soundboard sb){
		this.con = c;
		this.soundboard = sb;
	}
	
	public void playSound(String key){
		for(Sound temp : soundboard.getSounds()){
			if(key.equals(temp.getTitle())){
				player.playSound(con, temp.getUri());
			}
		}
	}
	
	public void setSoundboard(Soundboard sb){
		this.soundboard = sb;
	}
	
/*	public void downloadRingtone(String key){
		for(Sound temp : InfiniteSoundboards.getSounds()){
			if(key.equals(temp.getTitle())){
				ringtoneMaker.setRingtone(key,temp.getUri(), con);
			}
		}
	}*/
}
