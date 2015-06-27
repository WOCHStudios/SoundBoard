package com.wochstudios.soundboard.Controllers;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wochstudios.soundboard.R;

import java.util.ArrayList;

public class DrawerController
{
	
	
	public DrawerController(){
	}
		
	public void refreshDrawerList(ListView lv,ArrayList<String> l){
		lv.setAdapter(new ArrayAdapter<String>(lv.getContext(), R.layout.drawer_item, l));
	}
}
