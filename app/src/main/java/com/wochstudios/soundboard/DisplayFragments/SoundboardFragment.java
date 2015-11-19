package com.wochstudios.soundboard.DisplayFragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wochstudios.soundboard.Controllers.SoundboardController;
import com.wochstudios.soundboard.Listeners.ISoundboardFragmentListener;
import com.wochstudios.soundboard.MainActivity;
import com.wochstudios.soundboard.MainActivityHelper;
import com.wochstudios.soundboard.Models.Soundboard;
import com.wochstudios.soundboard.R;

import java.util.ArrayList;
import java.util.Collections;

public class SoundboardFragment extends Fragment
{
	private ArrayList<String> Titles;
	private SoundboardController SBC;
	private ListView soundListView;
	private ImageButton addSoundButton;
	private Soundboard soundboard;
	private ISoundboardFragmentListener listener;
	private ActionMode actionMode;
	
	public SoundboardFragment(){}
	public SoundboardFragment(Soundboard s){
		this.soundboard = s;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		init();
		View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        AdView ad = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);
		setupListView(rootView.findViewById(R.id.listSound));
		setupAddButton(rootView.findViewById(R.id.add_button));
		return rootView;
	}
	
	private void init(){
		SBC = new SoundboardController(getActivity(), soundboard);
		Titles = soundboard.getTitlesOfSounds();
		Collections.sort(Titles);
	}
	
	private void setupAddButton(View view){
		addSoundButton = (ImageButton)view;
		addSoundButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					((MainActivity)getActivity()).mainHelper.showDialogFragment(MainActivityHelper.ADD_SOUND_FRAGMENT_CD);
				}
		});
	}
	
	private void setupListView(View view){
		soundListView = (ListView) view;
		soundListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.listItemTxt, Titles));
		soundListView.setTextFilterEnabled(true);
		soundListView.setLongClickable(true);
		soundListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		soundListView.setOnItemClickListener(new ListViewClickListener());
		soundListView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View view, int position, long p4)
				{
					if(actionMode != null){
						return false;
					}
					soundListView.clearChoices();
					soundListView.setItemChecked(position, true);
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
		((ArrayAdapter<String>)soundListView.getAdapter()).clear();
		((ArrayAdapter<String>)soundListView.getAdapter()).addAll(Titles);
		((ArrayAdapter<String>)soundListView.getAdapter()).notifyDataSetChanged();		
		
	}

    public Soundboard getCurrentSoundboard(){
        return soundboard;
    }
	
	
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(actionMode == null) {
				SBC.playSound(Titles.get(position));
			}
			soundListView.setItemChecked(position,false);
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
					listener.onSoundRemoveCall(soundboard.getSoundByTitle(Titles.get(soundListView.getCheckedItemPosition())).getID()+"");
					mode.finish();
					return true;
				/*case R.id.set_rington_menu_item:
					SBC.downloadRingtone(Titles.get(soundListView.getCheckedItemPosition()));
					mode.finish();
					return true;*/
				default:
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			soundListView.setItemChecked(soundListView.getCheckedItemPosition(),false);
			actionMode = null;
		}

	}
}
