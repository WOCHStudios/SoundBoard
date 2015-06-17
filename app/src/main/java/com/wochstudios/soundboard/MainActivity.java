package com.wochstudios.soundboard;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wochstudios.soundboard.Controllers.MainController;
import com.wochstudios.soundboard.Database.SoundboardDBHelper;
import com.wochstudios.soundboard.Interfaces.IAddSoundDialogListener;

import java.util.ArrayList;
import java.util.Collections;
import android.content.*;
import android.util.*;
import com.wochstudios.soundboard.Controllers.*;
import com.wochstudios.soundboard.Models.*;

public class MainActivity extends FragmentActivity implements  IAddSoundDialogListener {

	private ArrayList<String> Titles;
	private MainController SBC;
	private DatabaseController DC;
	private AddSoundDialogFragment ASDF;
	private ListView lv;
	private String SoundboardId;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();	
	}//onCreate
	
	
	private void init(){
		SoundboardId ="1";
		DC = new DatabaseController(this);
		SBC = new MainController(this, DC.getSoundboard(SoundboardId));
		Titles = DC.getSoundboard(SoundboardId).getTitlesOfSounds();
		Collections.sort(Titles);
		createListView();
		
	}
	
	
	
	private void createListView(){
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,Titles));
		registerForContextMenu(lv);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new ListViewClickListener());
	}
	
	
	 @Override
	 public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
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
	   if(item.getItemId() == 0){
	   	SBC.downloadRingtone(Titles.get(info.position));
	   }else if(item.getItemId() == 1){
		   DC.removeSoundFromSoundboard(DC.getSoundboard(SoundboardId).getSoundByTitle(Titles.get(info.position)).getID()+"");
		 refreshListView();
	   }
	   return true;
	 }//onContextItemSelected
 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()){
			case R.id.add_sound:
				ASDF = new AddSoundDialogFragment(DC);
				ASDF.show(getFragmentManager(),"AddSoundDialogFragment");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		SBC.setSoundboard(DC.getSoundboard(SoundboardId));
		refreshListView();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog){
		
	}
	
	private void refreshListView(){
		Titles = DC.getSoundboard("1").getTitlesOfSounds();
		Collections.sort(Titles);
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,Titles));
	}


	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			SBC.playSound(Titles.get(position));
		} 
	}
}
