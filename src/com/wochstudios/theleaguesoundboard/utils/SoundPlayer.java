package com.wochstudios.theleaguesoundboard.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundPlayer {
	private MediaPlayer mp;
	
	public SoundPlayer(){
		mp = new MediaPlayer();
	}
	
	public boolean playSound(Context con, int id){
		try {
			if(!mp.isPlaying()){
				mp = MediaPlayer.create(con, id);
				//mp.prepare();
				mp.start();
				return true;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
