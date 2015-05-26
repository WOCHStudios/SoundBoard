package com.wochstudios.soundboard.utils;

import java.io.File;

import android.content.Context;
import android.util.Log;
import java.io.*;

public class MapUpdater
{
	public void updateSoundMapFile(String title, String path, Context con){
		try{
			String content = "\n"+title+":"+path;
			FileOutputStream fos = con.openFileOutput("filemap.txt", Context.MODE_APPEND);
			fos.write(content.getBytes());
			fos.close();
			
		}catch (IOException e){
			Log.e("Error","IOException");
		}
	}
	
}
