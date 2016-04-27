package com.wochstudios.InfiniteSoundboards;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.Toast;

import com.wochstudios.InfiniteSoundboards.Controllers.DatabaseController;
import com.wochstudios.InfiniteSoundboards.Controllers.DrawerController;
import com.wochstudios.InfiniteSoundboards.Fragments.AddSoundDialogFragment;
import com.wochstudios.InfiniteSoundboards.Fragments.CreateSoundboardFragment;
import com.wochstudios.InfiniteSoundboards.Fragments.SoundboardFragment;
import com.wochstudios.InfiniteSoundboards.Models.Sound;
import com.wochstudios.InfiniteSoundboards.Models.Soundboard;

import java.util.ArrayList;

public class MainActivityHelper
{
    public static final int CREATE_SOUNDBOARD_FRAGEMENT_CD = 1;
    public static final int ADD_SOUND_FRAGMENT_CD = 2;
    public static final int RENAME_SOUNDBOARD_FRAGMENT_CD =3;

    private static final String FRAGMENT_TAG = "current_fragment";
    private static final String CURRENT_SOUNDBOARD = "currentSoundboard";
    private static final String DEFAULT_SOUNDBOARD = "defaultSoundboard";

    private DatabaseController databaseController;
	private DrawerController drawerController;
	private Fragment fragment;
	private DialogFragment dialogFragment;
	private SharedPreferences preferences;
	private FragmentManager fragmentManager;
	private ListView drawerList;
    private MainActivity mainActivity;

    public MainActivityHelper(Activity activity){
		databaseController = new DatabaseController(activity);
		drawerController = new DrawerController();
		fragmentManager = activity.getFragmentManager();
		PreferenceManager.setDefaultValues(activity, R.xml.preferences, false);
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		drawerList = (ListView)activity.findViewById(R.id.left_drawer);
        mainActivity = (MainActivity) activity;
    }
	

	
	private void replaceContentFrameWithFragment(Fragment frag){
		fragmentManager.beginTransaction()
			.replace(R.id.content_frame,frag, FRAGMENT_TAG)
			.commit();
	}
	
	public void initSoundboardFragment(){
        Soundboard soundboard =databaseController.getSoundboard(preferences.getString(DEFAULT_SOUNDBOARD,""));
        if(soundboard.getTitle() == null){
            if(!databaseController.getSoundboards().isEmpty()){
               soundboard = databaseController.getSoundboards().get(0);
            }
        }
        if(soundboard != null) {
            fragment = new SoundboardFragment();
            ((SoundboardFragment)fragment).setArguments(soundboard);
            replaceContentFrameWithFragment(fragment);
        }

	}
	
	
	public void updateSoundboardFragment(String id){
		preferences.edit().putString(CURRENT_SOUNDBOARD,id).commit();
		((SoundboardFragment)fragment).refreshListView(databaseController.getSoundboard(id));
    }
	
	

	public void  updateDrawerList(){
        drawerController.refreshDrawerList(drawerList, databaseController.getSoundboards());
	}
	
	
	public void showDialogFragment(int fragment_cd, String name){
		switch (fragment_cd){
			case CREATE_SOUNDBOARD_FRAGEMENT_CD:
				dialogFragment = new CreateSoundboardFragment();
                ((CreateSoundboardFragment)dialogFragment).setArguments(databaseController);
				break;
			case ADD_SOUND_FRAGMENT_CD:
                if (databaseController.checkForSoundboards()) {
                    String id = (preferences.getString(CURRENT_SOUNDBOARD, "").isEmpty()) ?
                            preferences.getString(DEFAULT_SOUNDBOARD, "") : preferences.getString(CURRENT_SOUNDBOARD, "");
                    dialogFragment = new AddSoundDialogFragment();
                    ((AddSoundDialogFragment)dialogFragment).setArguments(databaseController,id);
                } else {
                    dialogFragment = new CreateSoundboardFragment();
                    ((CreateSoundboardFragment)dialogFragment).setArguments(databaseController);
                    Toast.makeText(mainActivity, "Please create a InfiniteSoundboards first", Toast.LENGTH_SHORT).show();
                }
                break;
/*            case RENAME_SOUNDBOARD_FRAGMENT_CD:
                dialogFragment = new RenameDialogFragment();
                ((RenameDialogFragment)dialogFragment).setArguments(name,this);
                break;*/
			default:
				return;
		}
		dialogFragment.show(fragmentManager, dialogFragment.getClass().getSimpleName());
	}
	
	public void startSettingsActivity(){
		Intent intent = new Intent();
		intent.putStringArrayListExtra("SOUNDBOARD_NAMES",getSoundboardNames());
		intent.putStringArrayListExtra("SOUNDBOARD_IDS", databaseController.getSoundboardIds());
		intent.setClass(mainActivity, SettingsActivity.class);
        mainActivity.startActivityForResult(intent, 0);
    }

	public void removeSoundboard(String id){
		Soundboard s = databaseController.getSoundboard(id);
		for(Sound sound : s.getSounds()){
			databaseController.removeSoundFromSoundboard(sound.getID()+"");
		}
		if(id.equals(preferences.getString(DEFAULT_SOUNDBOARD,""))){
			preferences.edit().putString(DEFAULT_SOUNDBOARD,"0").commit();
		}
		if(id.equals(getCurrentSoundboardId())){
			preferences.edit().putString(CURRENT_SOUNDBOARD,preferences.getString(DEFAULT_SOUNDBOARD,"")).commit();
		}
		databaseController.removeSoundboardFromDatabase(id);	
		updateDrawerList();
		updateSoundboardFragment(getCurrentSoundboardId());
	}
	public void removeSound(String id){databaseController.removeSoundFromSoundboard(id);}
		
	public ArrayList<String> getSoundboardNames(){
		return databaseController.getSoundboardNames();
	}

    public void renameSoundboard(String newName, String soundboardId) {
        databaseController.renameSoundboard(newName, soundboardId);
    }

	
	public String getCurrentSoundboardId(){
		return preferences.getString(CURRENT_SOUNDBOARD,"");
	}

	public String getCurrentSoundboardTitle(){
        if(((SoundboardFragment)fragment).getCurrentSoundboard()!= null){
            return ((SoundboardFragment)fragment).getCurrentSoundboard().getTitle();
        }else{
            return mainActivity.getResources().getString(R.string.app_name);
        }

    }
	
	public ActionBarDrawerToggle getToggle(Activity act, DrawerLayout dl, int open, int close){
        return drawerController.getToggle(act, dl, open, close);
	}

    public DatabaseController getDatabaseController(){
        return databaseController;
    }
    public FragmentManager getFragmentManager(){return fragmentManager;}


	}
