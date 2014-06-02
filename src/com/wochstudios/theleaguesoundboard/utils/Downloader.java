package com.wochstudios.theleaguesoundboard.utils;

import java.io.InputStream;

import android.content.Context;

public class Downloader {
	private FileWriter writer = new FileWriter();
	private RingtoneMaker maker = new RingtoneMaker();
	
	public Downloader(){
		
	}
	
	public void downloadRingtone(Context con, InputStream is, String name){
		writer.writeFile(is, name);
		maker.setRingtone(name, con);
	}
}
