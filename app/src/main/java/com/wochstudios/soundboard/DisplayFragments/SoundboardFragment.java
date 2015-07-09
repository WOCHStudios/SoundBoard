package com.wochstudios.soundboard.DisplayFragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.wochstudios.soundboard.Controllers.SoundboardController;
import com.wochstudios.soundboard.Interfaces.ISoundboardFragmentListener;
import com.wochstudios.soundboard.Models.Soundboard;
import com.wochstudios.soundboard.R;

import java.util.ArrayList;
import java.util.Collections;

public class SoundboardFragment extends Fragment
{
	private ArrayList<String> Titles;
	private SoundboardController SBC;
	private ListView lv;
	private Soundboard soundboard;
	private ISoundboardFragmentListener listener;
	
	public SoundboardFragment(Soundboard s){
		this.soundboard = s;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		init();
		View rootView = inflater.inflate(R.layout.fragment_main,container,false);
		setupListView(rootView.findViewById(R.id.listSound));
		return rootView;
	}
	
	private void init(){
		SBC = new SoundboardController(getActivity(), soundboard);
		Titles = soundboard.getTitlesOfSounds();
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
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			listener = (ISoundboardFragmentListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.toString()+ "must implement interface");
		}
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	   	if (v.getId()==R.id.listSound) {
	     	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	     	menu.setHeaderTitle(Titles.get(info.position));
	     	/*String[] menuItems = getResources().getStringArray(R.array.menuItems);
	     	for (int i = 0; i<menuItems.length; i++) {
	       		menu.add(Menu.NONE, i, i, menuItems[i]);
	     	}*/
			getActivity().getMenuInflater().inflate(R.menu.soundboard_action_menu,menu);
	   	}
		Toast.makeText(getActivity(),"ContextMenuCreated",Toast.LENGTH_LONG).show();
	}//onCreateContextMenu
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Toast.makeText(getActivity(),"ItemSelected",Toast.LENGTH_LONG).show();
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if(item.getItemId() == R.id.set_rington_menu_item){
			SBC.downloadRingtone(Titles.get(info.position));
		}else if(item.getItemId() == R.id.remove_sound_menu_item){
			listener.onSoundRemoveCall(soundboard.getSoundByTitle(Titles.get(info.position)).getID()+"");
		}
		return true;
	}//onContextItemSelected
	
	
	public void refreshListView(Soundboard s)
	{
		this.soundboard =s;
		SBC.setSoundboard(soundboard);
		Titles = soundboard.getTitlesOfSounds();
		Collections.sort(Titles);
		lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item,Titles));
	}
	
	
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			SBC.playSound(Titles.get(position));
		} 
	}
	
	
	
}
