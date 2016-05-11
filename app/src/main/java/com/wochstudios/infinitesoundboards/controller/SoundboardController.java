package com.wochstudios.infinitesoundboards.controller;

import android.content.Context;

import com.wochstudios.infinitesoundboards.models.Sound;
import com.wochstudios.infinitesoundboards.models.Soundboard;
import com.wochstudios.infinitesoundboards.utils.SoundPlayer;


public class SoundboardController {
	private SoundPlayer player = new SoundPlayer();
	//private RingtoneMaker ringtoneMaker = new RingtoneMaker();
	private Context con;
	private Soundboard soundboard;
	

	
	public SoundboardController(Context c, Soundboard sb){
		this.con = c;
		this.soundboard = sb;
	}
	
	public void playSound(Sound sound){
		player.playSound(con, sound.getUri());
	}
	
	public void setSoundboard(Soundboard sb){
		this.soundboard = sb;
	}

}
