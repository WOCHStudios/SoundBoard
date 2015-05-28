package com.wochstudios.soundboard.utils;

import java.io.*;

import android.content.Context;
import android.widget.*;

public class MapCreator
{
	private final String filename="filemap.txt";
	
	public void createMapFile(Context con){
		try{
			String content = "";
			if(!doesMapExists(con.openFileInput(filename))){
				FileOutputStream fos = con.openFileOutput(filename,Context.MODE_PRIVATE);
				fos.write(content.getBytes());
				fos.close();
			}
		}catch (IOException e){}
	}
	
	private boolean doesMapExists(InputStream in) throws IOException{
		boolean result = in == null ? false : true;
		in.close();
		return result;
	}
}
