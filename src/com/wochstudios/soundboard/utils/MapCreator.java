package com.wochstudios.soundboard.utils;

import java.io.*;

import android.content.Context;

public class MapCreator
{
	public void createMapFile(Context con) throws IOException{
		String filename = "filemap.txt";
		String content = "Test:test_file";
		FileOutputStream fos = con.openFileOutput(filename,Context.MODE_PRIVATE);
		fos.write(content.getBytes());
		fos.close();
	}
}
