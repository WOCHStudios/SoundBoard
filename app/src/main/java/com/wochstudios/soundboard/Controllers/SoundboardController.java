package com.wochstudios.soundboard.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.wochstudios.soundboard.Models.Sound;
import com.wochstudios.soundboard.Models.Soundboard;
import com.wochstudios.soundboard.utils.RingtoneMaker;
import com.wochstudios.soundboard.utils.SoundPlayer;

import java.util.HashMap;


public class SoundboardController {
	private SoundPlayer player = new SoundPlayer();
	private RingtoneMaker ringtoneMaker = new RingtoneMaker();
	private Context con;
	private Soundboard soundboard;
	

	
	public SoundboardController(Context c, Soundboard sb){
		this.con = c;
		this.soundboard = sb;
	}
	
	public void playSound(String key){
		Toast.makeText(con,"Soundboard ID: "+soundboard.getID(),Toast.LENGTH_SHORT).show();
		for(Sound temp : soundboard.getSounds()){
			if(key.equals(temp.getTitle())){
				player.playSound(con, temp.getUri());
			}
		}
	}
	
	public void setSoundboard(Soundboard sb){
		this.soundboard = sb;
	}
	
	public void downloadRingtone(String key){
		for(Sound temp : soundboard.getSounds()){
			if(key.equals(temp.getTitle())){
				ringtoneMaker.setRingtone(key,temp.getUri(), con);
			}
		}
	}
}
