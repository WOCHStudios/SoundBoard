package com.wochstudios.soundboard.Models;

import java.util.ArrayList;

public class Soundboard extends SoundboardModel
{
	private int _ID;
	private String Title;
	private String date_created;
	private ArrayList<Sound> sounds;
	
	public Soundboard(){}
	
	public Soundboard(int id, String t, String dc){
		super(id, t);
		this.date_created = dc;
		this.sounds = new ArrayList<Sound>();
	}
	
	public Soundboard(int id, String t, String dc, ArrayList<Sound> s){
		super(id,t);
		this.date_created = dc;
		this.sounds = s;
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
