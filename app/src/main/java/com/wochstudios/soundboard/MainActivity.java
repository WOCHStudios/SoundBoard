package com.wochstudios.soundboard;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wochstudios.soundboard.Controllers.DatabaseController;
import com.wochstudios.soundboard.Controllers.SoundboardController;
import com.wochstudios.soundboard.Interfaces.IDialogListener;
import com.wochstudios.soundboard.DisplayFragments.*;

import java.util.ArrayList;
import java.util.Collections;
import android.content.*;
import android.preference.*;
import android.widget.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import com.wochstudios.soundboard.Controllers.*;
import org.w3c.dom.*;
import com.wochstudios.soundboard.Interfaces.*;
import com.wochstudios.soundboard.ClickListeners.*;

public class MainActivity extends Activity implements IDialogListener, ISoundboardFragmentListener{

	private DatabaseController databaseController;
	private DrawerController drawerController;
	private DialogFragment dialogFragment;
	private SoundboardFragment soundboardFragment;
	private SharedPreferences preferences;
	
	
	private MainActivityHelper mainHelper;
	
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle toggle;
	
	private final static String CREATE_SOUNDBOARD_NAME = "CreateSoundboardFragment";
	private final static String ADD_SOUND_NAME = "AddSoundDialogFragment";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
		
		databaseController = new DatabaseController(this);
		checkForSoundboards();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.edit().putString("currentSoundboard",preferences.getString("defaultSoundboard","")).commit();		
		
		mainHelper = new MainActivityHelper(this);
		
		drawerController = new DrawerController();
		drawerLayout = (DrawerLayout)findViewById(R.id.container);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		setupDrawerList(drawerList);
		
		if(savedInstanceState == null){
			mainHelper.loadSoundBoardFragment(preferences.getString("defaultSoundboard",""));
			//updateFragment(R.id.content_frame,preferences.getString("defaultSoundboard",""));
		}
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.ic_drawer);
		
		toggle = new ActionBarDrawerToggle(
			this,
			drawerLayout,
			null,
			R.string.drawer_open,
			R.string.drawer_close);
		
		
			
	}//onCreate
	
	private void checkForSoundboards(){
		if(!databaseController.checkForSoundboards()){
			mainHelper.showDialogFragment(mainHelper.CREATE_SOUNDBOARD_FRAGEMENT_CD);
		}
	}
	
	private void setupDrawerList(ListView lv){
		lv.setAdapter(new ArrayAdapter<String>(lv.getContext(),R.layout.drawer_item,R.id.DrawerItemTxt,databaseController.getSoundboardNames()));
		lv.setOnItemClickListener(new DrawerOnItemClickListener(drawerLayout,mainHelper));
	}
	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(toggle.onOptionsItemSelected(item)){
			return true;
		}
		switch (item.getItemId()){
			case R.id.add_sound:
				mainHelper.showDialogFragment(mainHelper.ADD_SOUND_FRAGMENT_CD);
				return true;
			case R.id.settings:
				mainHelper.startSettingsActivity(this);
				return true;
			default:	
				return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		//soundboardFragment.refreshListView(databaseController.getSoundboard(preferences.getString("currentSoundboard","")));
		mainHelper.loadSoundBoardFragment(preferences.getString("currentSoundboard",""));
		drawerController.refreshDrawerList(drawerList, databaseController.getSoundboardNames());
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog)
	{}

	@Override
	public void onSoundRemoveCall(String soundID)
	{
		databaseController.removeSoundFromSoundboard(soundID);
		mainHelper.loadSoundBoardFragment(preferences.getString("currentSoundboard",""));	
	}	
	
}
