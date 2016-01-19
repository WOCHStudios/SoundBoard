package com.wochstudios.InfiniteSoundboards.Models;

import android.net.Uri;

public class Sound extends SoundboardModel
{
	private Uri uri;
	private String soundboard_id;

	public Sound(){}

	public Sound (int id, String t, Uri u, String sb_id){
		super(id,t);
		this.uri = u;
		this.soundboard_id = sb_id;
	}

	public void setSoundboardId(String sb_id)
	{
		this.soundboard_id = sb_id;
	}

	public String getSoundboardId()
	{
		return soundboard_id;
	}

	public void setUri(Uri uri)
	{
		this.uri = uri;
	}

	public Uri getUri()
	{
		return uri;
	}
}
