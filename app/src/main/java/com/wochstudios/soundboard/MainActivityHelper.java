package com.wochstudios.soundboard;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ListView;

import com.wochstudios.soundboard.Controllers.DatabaseController;
import com.wochstudios.soundboard.Controllers.DrawerController;
import com.wochstudios.soundboard.DisplayFragments.AddSoundDialogFragment;
import com.wochstudios.soundboard.DisplayFragments.CreateSoundboardFragment;
import com.wochstudios.soundboard.DisplayFragments.SoundboardFragment;


public class MainActivityHelper
{
	private DatabaseController databaseController;
	private DrawerController drawerController;
	private Fragment fragment;
	private DialogFragment dialogFragment;
	private SharedPreferences preferences;
	private FragmentManager fragmentManager;
	
	private final String FRAGMENT_TAG = "current_fragment";
	public static final int CREATE_SOUNDBOARD_FRAGEMENT_CD = 1;
	public static final int ADD_SOUND_FRAGMENT_CD = 2;
	
	public MainActivityHelper(Activity activity){
		databaseController = new DatabaseController(activity);
		drawerController = new DrawerController();
		fragmentManager = activity.getFragmentManager();
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
	}
	
	
	public void checkForSoundboards(){
		if(!databaseController.checkForSoundboards()){
			showDialogFragment(CREATE_SOUNDBOARD_FRAGEMENT_CD);
		}
	}
	
	private void replaceFragment(Fragment frag){
		fragmentManager.beginTransaction()
			.replace(R.id.content_frame,frag, FRAGMENT_TAG)
			.commit();
	}
	
	public void loadSoundBoardFragment(String soundboard_id){
		fragment = new SoundboardFragment(
			databaseController.getSoundboard(soundboard_id)
			);
		replaceFragment(fragment);	
	}

	public void  updateDrawerList(ListView view){
		drawerController.refreshDrawerList(view, databaseController.getSoundboardNames());
	}
	
	
	public void showDialogFragment(int fragment_cd){
		switch (fragment_cd){
			case CREATE_SOUNDBOARD_FRAGEMENT_CD:
				dialogFragment = new CreateSoundboardFragment(databaseController);
				break;
			case ADD_SOUND_FRAGMENT_CD:
				dialogFragment = new AddSoundDialogFragment(databaseController,preferences.getString("currentSoundboard",""));
				break;
			default:
				return;
		}
		dialogFragment.show(fragmentManager, dialogFragment.getClass().getSimpleName());
	}
	
	public void startSettingsActivity(Activity activity){
		Intent intent = new Intent();
		intent.setClass(activity, SettingsActivity.class);
		activity.startActivityForResult(intent,0);
	}

	public void removeSoundboard(String id){
		databaseController.removeSoundboardFromDatabase(id);
	}
		
}
