package com.wochstudios.soundboard.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundPlayer {
	private MediaPlayer mp;
	
	public SoundPlayer(){
		mp = new MediaPlayer();
	}
	
	public boolean playSound(Context con, Uri uri){
		try {
			if(!mp.isPlaying()){
				mp = MediaPlayer.create(con, uri);
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
