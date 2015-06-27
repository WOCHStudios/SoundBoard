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

import com.wochstudios.soundboard.ClickListeners.DrawerOnItemClickListener;
import com.wochstudios.soundboard.Controllers.DatabaseController;
import com.wochstudios.soundboard.Controllers.DrawerController;
import com.wochstudios.soundboard.DisplayFragments.AddSoundDialogFragment;
import com.wochstudios.soundboard.DisplayFragments.CreateSoundboardFragment;
import com.wochstudios.soundboard.Interfaces.IDialogListener;
import com.wochstudios.soundboard.Interfaces.ISoundboardFragmentListener;

public class MainActivity extends Activity implements IDialogListener, ISoundboardFragmentListener{

	private DatabaseController databaseController;
	private DrawerController drawerController;
	private SharedPreferences preferences;
	
	
	private MainActivityHelper mainHelper;
	
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle toggle;
	

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		mainHelper = new MainActivityHelper(this);
		
		databaseController = new DatabaseController(this);
		checkForSoundboards();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.edit().putString("currentSoundboard",preferences.getString("defaultSoundboard","")).commit();		
		

		
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
		lv.setOnItemClickListener(new DrawerOnItemClickListener(drawerLayout, mainHelper));
		registerForContextMenu(lv);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.left_drawer) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			String[] menuItems = getResources().getStringArray(R.array.drawerMenuItems);
			for (int i = 0; i<menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}//onCreateContextMenu

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		mainHelper.removeSoundboard(info.position+"");
		mainHelper.updateDrawerList(drawerList);
		return true;
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
		if(CreateSoundboardFragment.class.isInstance(dialog)){
			drawerController.refreshDrawerList(drawerList, databaseController.getSoundboardNames());
		}else if(AddSoundDialogFragment.class.isInstance(dialog)){
			mainHelper.loadSoundBoardFragment(preferences.getString("currentSoundboard",""));
		}
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
