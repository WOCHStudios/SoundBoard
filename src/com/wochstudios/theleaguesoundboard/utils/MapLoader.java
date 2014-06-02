package com.wochstudios.theleaguesoundboard.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MapLoader {
	
	public HashMap<String,String> loadVaules(InputStream valueFile) throws IOException{
		final int lhs = 0;
        final int rhs = 1;
        HashMap<String,String> valueMap = new HashMap<String,String>();
        BufferedReader  bfr = new BufferedReader(new InputStreamReader(valueFile));
        String line;
        while ((line = bfr.readLine()) != null) {
            if (!line.equals("")){
                String[] pair = line.trim().split(":");
                valueMap.put(pair[lhs].trim(), pair[rhs].trim());
            }
        }
        bfr.close();
        return valueMap;
	}
}
