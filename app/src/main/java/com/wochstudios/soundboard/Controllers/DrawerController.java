package com.wochstudios.soundboard.Controllers;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wochstudios.soundboard.R;

import java.util.ArrayList;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.app.Activity;
import android.content.Context;

public class DrawerController
{
	
	
	public DrawerController(){
	}
		
	public void refreshDrawerList(ListView lv,ArrayList<String> l){
		lv.setAdapter(new ArrayAdapter<String>(lv.getContext(), R.layout.drawer_item, l));
	}
	
	public ActionBarDrawerToggle getToggle(Activity con, DrawerLayout dl, int open, int close){
		return new ActionBarDrawerToggle(con, dl, null, open, close);
	}
}
