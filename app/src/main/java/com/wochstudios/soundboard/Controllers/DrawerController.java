package com.wochstudios.soundboard.Controllers;

import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.wochstudios.soundboard.*;
import java.util.*;

public class DrawerController
{
	
	
	public DrawerController(){
	}
		
	public void refreshDrawerList(ListView lv,ArrayList<String> l){
		lv.setAdapter(new ArrayAdapter<String>(lv.getContext(), R.layout.list_item, l));
	}
}
