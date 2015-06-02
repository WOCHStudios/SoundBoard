package com.wochstudios.soundboard.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

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
					//removeSoundFromMap(title,uri);
					break;
				default:
					break;
			}
		}catch (IOException e){
			Toast.makeText(con,"IOException",Toast.LENGTH_SHORT).show();
		}
	}
	
	private void addSoundToMap(String title, String uri) throws IOException{
			String content = "\n"+title+"="+uri;
			FileOutputStream fos = con.openFileOutput("filemap.txt", Context.MODE_APPEND);
			fos.write(content.getBytes());
			fos.close();	
	}
	
	public void removeSoundFromMap(String Title,HashMap<String,String> map){
		map.remove(Title);
		try{
			FileOutputStream fos = con.openFileOutput("filemap.txt", Context.MODE_PRIVATE);
			for(HashMap.Entry<String,String> entry : map.entrySet()){
				String line = entry.getKey()+"="+entry.getValue();
				fos.write(line.getBytes());
			}
			fos.close();
		}catch (IOException e){}
	}
	
}
