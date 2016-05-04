package com.wochstudios.infinitesoundboards.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wochstudios.infinitesoundboards.adapters.SoundCursorAdapter;
import com.wochstudios.infinitesoundboards.controller.SoundboardController;
import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.listeners.ISoundboardFragmentListener;
import com.wochstudios.infinitesoundboards.MainActivity;
import com.wochstudios.infinitesoundboards.MainActivityHelper;
import com.wochstudios.infinitesoundboards.models.Soundboard;
import com.wochstudios.infinitesoundboards.R;
import com.wochstudios.infinitesoundboards.service.MediaPlayerService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoundboardFragment extends Fragment
{
	//private SoundboardController SBC;
	private SoundCursorAdapter soundAdapter;
	private Soundboard soundboard;
	private ISoundboardFragmentListener listener;
	private ActionMode actionMode;
	private Uri soundsForSoundboardUri;
	private MediaPlayerService service;
	private Intent mediaPlayerIntent;
	private boolean bound;

	@BindView(R.id.listSound) ListView soundListView;
    @BindView(R.id.listSound_EmptyView) TextView emptyView;
	@BindView(R.id.add_button) FloatingActionButton addSoundButton;
	@BindView(R.id.adView) AdView ad;


	
	public SoundboardFragment(){

	}

    public void setArguments(Soundboard s){
        this.soundboard =s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_main,container,false);
		ButterKnife.bind(this, rootView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);
		mediaPlayerIntent = new Intent(getActivity(), MediaPlayerService.class);
		init();
		return rootView;
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
	public void onStart() {
		super.onStart();
		getActivity().bindService(mediaPlayerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onStop() {
		super.onStop();
		if(bound){
			getActivity().unbindService(serviceConnection);
		}
	}

	private void init(){
		//SBC = new SoundboardController(getActivity(), soundboard);
		soundsForSoundboardUri = SoundboardContract.SoundsTable.buildSoundsFromSoundboardUri(soundboard.getID()+"");
		setupListView();
		setupAddButton();
	}

	private void setupAddButton(){
		addSoundButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {
				((MainActivity) getActivity()).mainHelper.showDialogFragment(MainActivityHelper.ADD_SOUND_FRAGMENT_CD, "");
			}
		});
	}

	private void setupListView(){
		setupEmptyView();
		soundListView.setEmptyView(emptyView);
		Cursor cur = getActivity().getContentResolver().query(soundsForSoundboardUri,null,null,null,null);
		soundAdapter =new SoundCursorAdapter(getActivity(),cur,0);

		soundListView.setAdapter(soundAdapter);
		soundListView.setOnItemClickListener(new ListViewClickListener());
		soundListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> p1, View view, int position, long p4) {
				if (actionMode != null) {
					return false;
				}
				soundListView.clearChoices();
				soundListView.setItemChecked(position, true);
				actionMode = getActivity().startActionMode(new SoundboardActionModeCallback());

				return true;
			}
		});
	}


	private void setupEmptyView(){
		if(soundboard.getTitle() == null){
			emptyView.setText("Please select a Soundboard!");
		}else{
			emptyView.setText("Please add sounds to infinitesoundboards!");
		}
	}

	private void updateEmptyView(){
		if(soundboard.getTitle() == null){
			emptyView.setText("Please select a Soundboard!");
		}else{
			emptyView.setText("Please add sounds to infinitesoundboards!");
		}
	}

	public void refreshListView(Soundboard s)
	{
        this.soundboard =s;
        updateEmptyView();
		//SBC.setSoundboard(soundboard);
		soundsForSoundboardUri = SoundboardContract.SoundsTable.buildSoundsFromSoundboardUri(soundboard.getID()+"");
		soundAdapter.changeCursor(getActivity().getContentResolver().query(soundsForSoundboardUri,null,null,null,null));
		soundAdapter.notifyDataSetChanged();

		
	}

    public Soundboard getCurrentSoundboard(){
        return soundboard;
    }
	
	
	//TODO:Extract to its own class file
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(actionMode == null) {
				//SBC.playSound(soundAdapter.getSound(position));
				mediaPlayerIntent.putExtra(MediaPlayerService.soundUri,soundAdapter.getSound(position).getUri().toString());
				try{
					service.setupMediaPlayer(mediaPlayerIntent);
					service.startMediaPlayer();
				}catch (IOException e){
					e.printStackTrace();
				}

			}
			soundListView.setItemChecked(position,false);
		} 
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder1) {
			MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) binder1;
			service = binder.getService();
			service.startService(mediaPlayerIntent);
			bound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			bound = false;
		}
	};


	//TODO: Extract to own class
	private class SoundboardActionModeCallback implements ActionMode.Callback
	{

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{

			mode.getMenuInflater().inflate(R.menu.soundboard_action_menu,menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode p1, Menu p2)
		{

			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			switch(item.getItemId()){
				case R.id.remove_sound_menu_item:
					listener.onSoundRemoveCall(soundAdapter.getSound(soundListView.getCheckedItemPosition()).getID()+"");
					mode.finish();
					return true;
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
