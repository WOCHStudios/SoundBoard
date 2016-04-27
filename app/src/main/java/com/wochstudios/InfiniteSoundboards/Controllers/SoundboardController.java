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
	
	public void playSound(Sound sound){
		player.playSound(con, sound.getUri());
	}
	
	public void setSoundboard(Soundboard sb){
		this.soundboard = sb;
	}

}
