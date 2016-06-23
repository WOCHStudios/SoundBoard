package com.wochstudios.InfiniteSoundboards.controller;

import android.content.Context;

import com.wochstudios.InfiniteSoundboards.models.Sound;
import com.wochstudios.InfiniteSoundboards.models.Soundboard;
import com.wochstudios.InfiniteSoundboards.utils.SoundPlayer;


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
