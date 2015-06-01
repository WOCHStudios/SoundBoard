package com.wochstudios.soundboard.utils;

import java.io.File;

import android.content.Context;
import android.util.Log;
import java.io.*;
import android.widget.*;

public class MapUpdater
{
	public static final int ADD_CODE = 1;
	public static final int REMOVE_CODE = 2;
	private Context con;
	
	public MapUpdater(Context c){
		this.con = c;
	}
	
	public void updateSoundMap(int actionCode, String title, String uri){
		try{
			switch (actionCode){
				case ADD_CODE:
					addSoundToMap(title, uri);
					break;
				case REMOVE_CODE:
					removeSoundFromMap(title,uri);
					break;
				default:
					break;
			}
		}catch (IOException e){
			Toast.makeText(con,"IOException",Toast.LENGTH_SHORT).show();
		}
	}
	
	private void addSoundToMap(String title, String uri) throws IOException{
			String content = "\n"+title+":"+uri;
			FileOutputStream fos = con.openFileOutput("filemap.txt", Context.MODE_APPEND);
			fos.write(content.getBytes());
			fos.close();	
	}
	
	private void removeSoundFromMap(String Title,String uri) throws IOException{
		BufferedReader bfr = new BufferedReader(new InputStreamReader(con.openFileInput("filemap.txt")));
		FileOutputStream fos = con.openFileOutput("filemap.txt", Context.MODE_PRIVATE);
		String line = null;
		String removedLine = Title+":"+uri;
		while((line = bfr.readLine())!= null){
			if (!line.equals(removedLine)){
				fos.write(line.getBytes());
			}
		}
		fos.close();
		bfr.close();
	}
	
}
