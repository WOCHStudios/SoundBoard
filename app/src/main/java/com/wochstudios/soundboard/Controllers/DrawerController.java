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
		((ArrayAdapter<String>)lv.getAdapter()).clear();
		((ArrayAdapter<String>)lv.getAdapter()).addAll(l);	
		((ArrayAdapter<String>)lv.getAdapter()).notifyDataSetChanged();
	}
	
	public ActionBarDrawerToggle getToggle(Activity con, DrawerLayout dl, int open, int close){
		return new ActionBarDrawerToggle(con, dl, null, open, close);
	}
}
