package com.wochstudios.theleaguesoundboard;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class MainActivity extends Activity {

	private ArrayList<String> Titles;
	private MainController MC;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MC = new MainController(this);
		Titles = new ArrayList<String>(MC.getMapKeys());
		Collections.sort(Titles);
		//Titles = new String[]{"TEST 1", "TEST 2", "TEST 3","TEST 4","TEST 5"};

		
		
		//creates and sets the click listener for list view
		final ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,Titles));
		registerForContextMenu(lv);
		lv.setTextFilterEnabled(true);
		  lv.setOnItemClickListener(new OnItemClickListener() {
			    public void onItemClick(AdapterView<?> parent, View view,
			        int position, long id) {
			    	Log.d("ListItem:OnClick", Titles.get(position));	
			    	MC.playSound(Titles.get(position));
			    } 
			  });	  
	}//onCreate
	
	
	 @Override
	 public void onCreateContextMenu(ContextMenu menu, View v,
	     ContextMenuInfo menuInfo) {
	   if (v.getId()==R.id.listView1) {
	     AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	     menu.setHeaderTitle(Titles.get(info.position));
	     String[] menuItems = getResources().getStringArray(R.array.menuItems);
	     for (int i = 0; i<menuItems.length; i++) {
	       menu.add(Menu.NONE, i, i, menuItems[i]);
	     }
	   }
	 }//onCreateContextMenu
	 
	 @Override
	 public boolean onContextItemSelected(MenuItem item) {
	   AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	   MC.downloadRingtone(Titles.get(info.position));
	   return true;
	 }//onContextItemSelected
 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}// onCreateOptionsMenu

}
