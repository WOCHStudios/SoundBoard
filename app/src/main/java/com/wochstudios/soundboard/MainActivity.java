package com.wochstudios.soundboard;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wochstudios.soundboard.ClickListeners.DrawerOnItemClickListener;
import com.wochstudios.soundboard.Controllers.DatabaseController;
import com.wochstudios.soundboard.Controllers.DrawerController;
import com.wochstudios.soundboard.DisplayFragments.AddSoundDialogFragment;
import com.wochstudios.soundboard.DisplayFragments.CreateSoundboardFragment;
import com.wochstudios.soundboard.Interfaces.IDialogListener;
import com.wochstudios.soundboard.Interfaces.ISoundboardFragmentListener;

public class MainActivity extends Activity implements IDialogListener, ISoundboardFragmentListener{

	private MainActivityController mainHelper;
	
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle toggle;
	

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		mainHelper = new MainActivityController(this);
		mainHelper.checkForSoundboards();
		drawerLayout = (DrawerLayout)findViewById(R.id.container);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		setupDrawerList(drawerList);
		setupActionBar();
		if(savedInstanceState == null){
			mainHelper.initSoundboardFragment();
		}	
	}//onCreate
	
	
	private void setupActionBar(){
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.ic_drawer);
		toggle = mainHelper.getToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
	}

	private void setupDrawerList(ListView lv){
		lv.setAdapter(new ArrayAdapter<String>(lv.getContext(),R.layout.drawer_item,R.id.DrawerItemTxt,mainHelper.getSoundboardNames()));
		DrawerOnItemClickListener listener = new DrawerOnItemClickListener(drawerLayout, mainHelper);
		lv.setOnItemClickListener(listener);
		lv.setOnItemLongClickListener(listener);
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
		if(CreateSoundboardFragment.class.isInstance(dialog)){
			mainHelper.updateDrawerList();
			//drawerController.refreshDrawerList(drawerList, mainHelper.getSoundboardNames());
		}else if(AddSoundDialogFragment.class.isInstance(dialog)){
			mainHelper.updateSoundboardFragment(((AddSoundDialogFragment)dialog).getSoundboardID());
		}
	}


	@Override
	public void onSoundRemoveCall(String soundID)
	{
		mainHelper.removeSound(soundID);
		mainHelper.updateSoundboardFragment(mainHelper.getCurrentSoundboardId());	
	}	
	
}
