package com.wochstudios.soundboard.Models;

import java.util.ArrayList;

public class Soundboard implements ISoundboardModel
{
	private int _ID;
	private String name;
	private String date_created;
	private ArrayList<Sound> sounds;
	
	public Soundboard(){}
	
	public Soundboard(int id, String n, String dc){
		this._ID = id;
		this.name = n;
		this.date_created = dc;
		this.sounds = new ArrayList<Sound>();
	}
	
	public Soundboard(int id, String n, String dc, ArrayList<Sound> s){
		this._ID = id;
		this.name = n;
		this.date_created = dc;
		this.sounds = s;
	}

	public void setID(int iD)
	{
		_ID = iD;
	}

	public int getID()
	{
		return _ID;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setDate_created(String date_created)
	{
		this.date_created = date_created;
	}

	public String getDate_created()
	{
		return date_created;
	}

	public void setSounds(ArrayList<Sound> sounds)
	{
		this.sounds = sounds;
	}

	public ArrayList<Sound> getSounds()
	{
		return sounds;
	}

	public ArrayList<String> getTitlesOfSounds(){
		ArrayList<String> titleList = new ArrayList<String>();
		for(Sound s : sounds){
			titleList.add(s.getTitle());
		}
		return titleList;
	}
	
	public Sound getSoundByTitle(String title){
		for(Sound s : sounds){
			if(s.getTitle().equals(title)){
				return s;
			}
		}
		return new Sound();
	}
	
	
	
	
	
}
