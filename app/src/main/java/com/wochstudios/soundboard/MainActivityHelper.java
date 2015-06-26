package com.wochstudios.soundboard;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.app.*;
import android.preference.*;
import android.content.Intent;

import com.wochstudios.soundboard.Controllers.*;
import com.wochstudios.soundboard.DisplayFragments.*;



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
		
}
