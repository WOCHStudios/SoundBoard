package com.wochstudios.soundboard.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;

import com.wochstudios.soundboard.Models.Sound;

public class MapLoader {
	private final int KEY = 0;
	private final int VALUE = 1;
	private HashMap<String,String> valueMap = new HashMap<String,String>();
	
	public HashMap<String,String> loadVaules(InputStream valueFile) throws IOException{
        BufferedReader  bfr = new BufferedReader(new InputStreamReader(valueFile));
		readBufferIntoMap(bfr);
        bfr.close();
        return valueMap;
	}
	
	public HashMap<String,String> loadValues(ArrayList<Sound> sounds){
		for(Sound s : sounds){
			valueMap.put(s.getTitle(),s.getUri().toString());
		}
		return null;
	}
	
	private void readBufferIntoMap(BufferedReader bfr) throws IOException{
		String line = "";
		while((line = bfr.readLine()) != null){
			if(!line.isEmpty()){
				String[] pair = line.trim().split("=");
				valueMap.put(pair[KEY],pair[VALUE]);
			}
		}
	}
}
