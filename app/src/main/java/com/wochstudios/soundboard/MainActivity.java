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

public class MainActivity extends Activity implements IDialogListener, ISoundboardFragmentListener{

	private DatabaseController DC;
	private DrawerController DrC;
	private DialogFragment DF;
	private SoundboardFragment mfragment;
	private SharedPreferences shrPre;
	
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle toggle;
	
	private final static String CREATE_SOUNDBOARD_NAME = "CreateSoundboardFragment";
	private final static String ADD_SOUND_NAME = "AddSoundDialogFragment";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
		
		DC = new DatabaseController(this);
		checkForSoundboards();
		shrPre = PreferenceManager.getDefaultSharedPreferences(this);
		shrPre.edit().putString("currentSoundboard",shrPre.getString("defaultSoundboard","")).commit();		
		
		DrC = new DrawerController();
		mDrawerLayout = (DrawerLayout)findViewById(R.id.container);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		setupDrawerList(mDrawerList);
		
		if(savedInstanceState == null){
			updateFragment(R.id.content_frame,shrPre.getString("defaultSoundboard",""));
		}
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.ic_drawer);
		
		toggle = new ActionBarDrawerToggle(
			this,
			mDrawerLayout,
			null,
			R.string.drawer_open,
			R.string.drawer_close);
		
		
			
	}//onCreate
	
	private void checkForSoundboards(){
		if(!DC.checkForSoundboards()){
			launchDialogFragment(CREATE_SOUNDBOARD_NAME);
		}
	}
	
	private void setupDrawerList(ListView lv){
		lv.setAdapter(new ArrayAdapter<String>(lv.getContext(),R.layout.drawer_item,R.id.DrawerItemTxt,DC.getSoundboardNames()));
		lv.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView parent, View view, int position,long id){
					switch(position){
						case 0:
							launchDialogFragment(CREATE_SOUNDBOARD_NAME);
							mDrawerLayout.closeDrawer(mDrawerList);
							break;
						default:
							shrPre.edit().putString("currentSoundboard",""+position).commit();
							Toast.makeText(view.getContext(),""+position,Toast.LENGTH_SHORT).show();
							mfragment.setSoundboard(DC.getSoundboard(position+""));
							mfragment.refreshListView(DC.getSoundboard(position+""));
							mDrawerLayout.closeDrawer(mDrawerList);							
							break;
					}
				}
			});
	}
	
	private void updateFragment(int fragement_id, String soundboard_id){
		//mfragment = new SoundboardFragment(DC,soundboard_id);
		mfragment = new SoundboardFragment(DC.getSoundboard(soundboard_id));
		getFragmentManager().beginTransaction()
			.add(fragement_id,mfragment)
				.commit();
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
				launchDialogFragment(ADD_SOUND_NAME);
				return true;
			case R.id.settings:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent,0);
				return true;
			default:	
				return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		mfragment.refreshListView(DC.getSoundboard(shrPre.getString("currentSoundboard","")));
		DrC.refreshDrawerList(mDrawerList, DC.getSoundboardNames());
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog)
	{}

	@Override
	public void onSoundRemoveCall(String soundID)
	{
		DC.removeSoundFromSoundboard(soundID);
		mfragment.refreshListView(DC.getSoundboard(shrPre.getString("currentSoundboard","")));
	}	
	
	public void launchDialogFragment(String name){
		switch(name){
			case CREATE_SOUNDBOARD_NAME:
				DF = new CreateSoundboardFragment(DC);
				DF.show(getFragmentManager(),CREATE_SOUNDBOARD_NAME);
				break;
			case ADD_SOUND_NAME:
				DF = new AddSoundDialogFragment(DC,shrPre.getString("currentSoundboard",""));
				DF.show(getFragmentManager(), ADD_SOUND_NAME);
				break;
			default:
				break;
		}
	}
}
