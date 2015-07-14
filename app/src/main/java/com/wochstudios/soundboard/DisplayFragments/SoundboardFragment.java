package com.wochstudios.soundboard.DisplayFragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wochstudios.soundboard.Controllers.SoundboardController;
import com.wochstudios.soundboard.Interfaces.ISoundboardFragmentListener;
import com.wochstudios.soundboard.Models.Soundboard;
import com.wochstudios.soundboard.R;

import java.util.ArrayList;
import java.util.Collections;
import android.widget.*;
import android.widget.Toast;

public class SoundboardFragment extends Fragment
{
	private ArrayList<String> Titles;
	private SoundboardController SBC;
	private ListView lv;
	private Soundboard soundboard;
	private ISoundboardFragmentListener listener;
	private ActionMode actionMode;
	
	public SoundboardFragment(){}
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
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.listItemTxt, Titles));
		lv.setTextFilterEnabled(true);
		lv.setLongClickable(true);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setOnItemClickListener(new ListViewClickListener());
		lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View view, int position, long p4)
				{
					if(actionMode != null){
						return false;
					}
					lv.clearChoices();
					lv.setItemChecked(position, true);
					actionMode = getActivity().startActionMode(new SoundboardActionModeCallback());
					
					return true;
				}
		});
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

	public void refreshListView(Soundboard s)
	{
		this.soundboard =s;
		SBC.setSoundboard(soundboard);
		Titles = soundboard.getTitlesOfSounds();
		Collections.sort(Titles);
		((ArrayAdapter<String>)lv.getAdapter()).clear();
		((ArrayAdapter<String>)lv.getAdapter()).addAll(Titles);
		((ArrayAdapter<String>)lv.getAdapter()).notifyDataSetChanged();		
		
	}
	
	
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			Toast.makeText(view.getContext(),"LV Checked: "+lv.getCheckedItemPosition()+" LV Selected: "+lv.getSelectedItemPosition(),Toast.LENGTH_LONG).show();
			if(actionMode == null) {
				SBC.playSound(Titles.get(position));
			}
			lv.setItemChecked(position,false);
		} 
	}
	
	private class SoundboardActionModeCallback implements ActionMode.Callback
	{

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			// TODO: Implement this method
			mode.getMenuInflater().inflate(R.menu.soundboard_action_menu,menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode p1, Menu p2)
		{
			// TODO: Implement this method
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			switch(item.getItemId()){
				case R.id.remove_sound_menu_item:
					listener.onSoundRemoveCall(soundboard.getSoundByTitle(Titles.get(lv.getCheckedItemPosition())).getID()+"");
					mode.finish();
					return true;
				case R.id.set_rington_menu_item:
					SBC.downloadRingtone(Titles.get(lv.getCheckedItemPosition()));
					mode.finish();
					return true;
				default:
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			lv.setItemChecked(lv.getCheckedItemPosition(),false);
			actionMode = null;
		}

	}
}
