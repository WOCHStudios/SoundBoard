package com.wochstudios.soundboard.utils;

import java.io.*;

import android.content.Context;
import android.widget.*;

public class MapCreator
{
	public void createMapFile(Context con) throws IOException{
		String filename = "filemap.txt";
		String content = "";
		if(!doesMapExists(con.openFileInput(filename))){
			FileOutputStream fos = con.openFileOutput(filename,Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		}	
	}
	
	private boolean doesMapExists(InputStream in) throws IOException{
		boolean result = in == null ? false : true;
		in.close();
		return result;
	}
}
