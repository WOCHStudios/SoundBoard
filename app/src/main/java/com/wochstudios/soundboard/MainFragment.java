package com.wochstudios.soundboard;

import android.support.v4.app.Fragment;
import android.app.DialogFragment;
import android.view.*;
import android.view.ContextMenu.*;
import android.os.*;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


import com.wochstudios.soundboard.Controllers.*;

import java.util.ArrayList;
import java.util.Collections;
import android.widget.*;
import com.wochstudios.soundboard.Interfaces.*;
import android.app.*;

public class MainFragment extends Fragment implements IAddSoundDialogListener
{
	private ArrayList<String> Titles;
	private MainController SBC;
	private DatabaseController DC;
	private ListView lv;
	private String SoundboardId;
	
	public MainFragment()
	{}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		init();
		View rootView = inflater.inflate(R.layout.fragment_main,container,false);
		setupListView(rootView.findViewById(R.id.listView1));
		return rootView;
	}
	
	private void init(){
		SoundboardId = "1";
		DC = new DatabaseController(getActivity());
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
	
	
	private void refreshListView()
	{
		Titles = DC.getSoundboard(SoundboardId).getTitlesOfSounds();
		Collections.sort(Titles);
		lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item,Titles));
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		SBC.setSoundboard(DC.getSoundboard(SoundboardId));
		refreshListView();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog){}

	
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			SBC.playSound(Titles.get(position));
		} 
	}
	
}
