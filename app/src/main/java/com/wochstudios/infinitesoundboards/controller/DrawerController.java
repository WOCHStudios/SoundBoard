package com.wochstudios.infinitesoundboards.controller;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wochstudios.infinitesoundboards.models.Soundboard;

import java.util.ArrayList;

public class DrawerController
{
	
	public DrawerController(){
	}

	public ActionBarDrawerToggle getToggle(Activity con, DrawerLayout dl, int open, int close){
		return new ActionBarDrawerToggle(con, dl, null, open, close);
	}


}
