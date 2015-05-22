package com.wochstudios.soundboard.utils;

import java.io.InputStream;

import android.content.Context;

public class Downloader {
	private FileCopier writer = new FileCopier();
	private RingtoneMaker maker = new RingtoneMaker();
	
	public Downloader(){
		
	}
	
	public void downloadRingtone(Context con, InputStream is, String name){
		writer.copyFileToSDCard(is, name);
		maker.setRingtone(name, con);
	}
}
