package com.wochstudios.InfiniteSoundboards.controller;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

public class DrawerController
{
	
	public DrawerController(){
	}

	public ActionBarDrawerToggle getToggle(Activity con, DrawerLayout dl, int open, int close){
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(con, dl, null, open, close);
		toggle.syncState();
		return toggle;
	}


}
