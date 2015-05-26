package com.wochstudios.soundboard;

import android.net.Uri;
import android.content.Context;
import android.content.ContentResolver;

public class AddSoundController
{
	private Context con;
	public AddSoundController(Context c){
		this.con = c;
	}
	
	public void AddSoundToFile(String title, String path){
		
	}
	
	public Uri getResourceUri(){
		int id = con.getResources().getIdentifier("filemap", "raw", con.getPackageName());
		Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+
							con.getResources().getResourcePackageName(id)+'/'+
							con.getResources().getResourceTypeName(id)+'/'+
							con.getResources().getResourceEntryName(id));
		return uri;
	}
	
}
