package com.wochstudios.soundboard.DisplayFragments;

import android.app.Fragment;
import android.app.DialogFragment;
import android.view.*;
import android.view.ContextMenu.*;
import android.os.*;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


import com.wochstudios.soundboard.Controllers.*;
import com.wochstudios.soundboard.R;

import java.util.ArrayList;
import java.util.Collections;
import android.widget.*;
import com.wochstudios.soundboard.Interfaces.*;
import android.app.*;
import android.content.*;
import android.preference.*;

public class MainFragment extends Fragment
{
	private ArrayList<String> Titles;
	private MainController SBC;
	private DatabaseController DC;
	private ListView lv;
	private String SoundboardId;
	
	public MainFragment(DatabaseController c, String id)
	{
		this.SoundboardId = id;
		this.DC = c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		init();
		View rootView = inflater.inflate(R.layout.fragment_main,container,false);
		setupListView(rootView.findViewById(R.id.listView1));
		return rootView;
	}
	
	private void init(){
		SBC = new MainController(getActivity(), DC.getSoundboard(SoundboardId));
		Titles = DC.getSoundboard(SoundboardId).getTitlesOfSounds();
		Collections.sort(Titles);
	}
	
	private void setupListView(View view){
		lv = (ListView) view;
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item,R.id.listItemTxt,Titles));
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
	
	
	public void refreshListView()
	{
		SBC.setSoundboard(DC.getSoundboard(SoundboardId));
		Titles = DC.getSoundboard(SoundboardId).getTitlesOfSounds();
		Collections.sort(Titles);
		lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item,Titles));
	}
	
	
	public void setSoundboardId(String id){
		this.SoundboardId = id;
	}
	
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			SBC.playSound(Titles.get(position));
		} 
	}
	
	
	
}
