package com.wochstudios.theleaguesoundboard.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;

public class FileWriter {
	
	public File writeFile(InputStream stream,String name){
		 	
		 try {
			   InputStream ins = stream;
			   byte[] buffer = new byte[ins.available()];
			   ins.read(buffer);
			   ins.close();
			   String filename = Environment.getExternalStorageDirectory().toString()+File.separator+"download"+File.separator+name+".mp3";
			   FileOutputStream fos = new FileOutputStream(filename);
			   fos.write(buffer);
			   fos.close();
			      
		   } catch (IOException io) {
			   //Toast.makeText(con.getApplicationContext(),"Download failed", Toast.LENGTH_SHORT).show();
		   }
		   return new File(Environment.getExternalStorageDirectory().toString()+File.separator+"download"+File.separator+name+".mp3");
		    
	}
	
}
