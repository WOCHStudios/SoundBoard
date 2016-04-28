package com.wochstudios.infinitesoundboards.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.wochstudios.infinitesoundboards.database.SounboardContract;
import com.wochstudios.infinitesoundboards.listeners.ISoundboardFragmentListener;
import com.wochstudios.infinitesoundboards.MainActivity;
import com.wochstudios.infinitesoundboards.MainActivityHelper;
import com.wochstudios.infinitesoundboards.models.Soundboard;
import com.wochstudios.infinitesoundboards.R;

public class SoundboardFragment extends Fragment
{
	private SoundboardController SBC;
	private SoundCursorAdapter soundAdapter;
	private Soundboard soundboard;
	private ISoundboardFragmentListener listener;
	private ActionMode actionMode;
	private Uri soundsForSoundboardUri;

	private ListView soundListView;
    private TextView emptyView;
	private FloatingActionButton addSoundButton;
	private AdView ad;


	
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
		init();
		View rootView = inflater.inflate(R.layout.fragment_main,container,false);
		//ButterKnife.bind(this, rootView);
       	ad = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);

		setupListView(rootView.findViewById(R.id.listSound), rootView.findViewById(R.id.listSound_EmptyView));
		setupAddButton(rootView.findViewById(R.id.add_button));
		return rootView;
	}
	
	private void init(){
		SBC = new SoundboardController(getActivity(), soundboard);
		soundsForSoundboardUri = SounboardContract.SoundsTable.buildSoundsFromSoundboardUri(soundboard.getID()+"");
	}
	
	private void setupAddButton(View view){
		addSoundButton = (FloatingActionButton)view;
		addSoundButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                ((MainActivity) getActivity()).mainHelper.showDialogFragment(MainActivityHelper.ADD_SOUND_FRAGMENT_CD, "");
            }
        });
	}
	
	private void setupListView(View list, View empty){
		soundListView = (ListView) list;
        setupEmptyView(empty);
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


    private void setupEmptyView(View empty){
        emptyView = (TextView)empty;
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
        updateEmptyView();
		SBC.setSoundboard(soundboard);
		soundAdapter.changeCursor(getActivity().getContentResolver().query(soundsForSoundboardUri,null,null,null,null));
		soundAdapter.notifyDataSetChanged();

		
	}

    public Soundboard getCurrentSoundboard(){
        return soundboard;
    }
	
	
	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(actionMode == null) {
				SBC.playSound(soundAdapter.getSound(position));
			}
			soundListView.setItemChecked(position,false);
		} 
	}
	
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
