package com.wochstudios.soundboard.Models;

import android.net.Uri;

public class Sound implements ISoundboardModel
{
	private int _ID;
	private String Title;
	private Uri uri;
	private String soundboard_id;
	
	public Sound (int id, String t, Uri u, String sb_id){
		this._ID = id;
		this.Title = t;
		this.uri = u;
		this.soundboard_id = sb_id;
	}

	public void setSb_id(String sb_id)
	{
		this.soundboard_id = sb_id;
	}

	public String getSb_id()
	{
		return soundboard_id;
	}

	public void setID(int iD)
	{
		_ID = iD;
	}

	public int getID()
	{
		return _ID;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getTitle()
	{
		return Title;
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
