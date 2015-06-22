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
import com.wochstudios.soundboard.Controllers.MainController;
import com.wochstudios.soundboard.Interfaces.IAddSoundDialogListener;
import com.wochstudios.soundboard.DisplayFragments.*;

import java.util.ArrayList;
import java.util.Collections;
import android.content.*;
import android.preference.*;
import android.widget.*;

public class MainActivity extends Activity implements IAddSoundDialogListener{

	private DatabaseController DC;
	private AddSoundDialogFragment ASDF;
	private CreateSoundboardFragment CSBF;
	private MainFragment mfragment;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DC = new DatabaseController(this);
		checkForSoundboards();
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		pref.edit().putString("currentSoundBoard", pref.getString("defaultSoundboard","")).commit();
		mfragment = new MainFragment(DC, pref.getString("currentSoundboard",""));
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction()
				.add(R.id.container,mfragment)
				.commit();
		}
			
	}//onCreate
	
	private void checkForSoundboards(){
		if(DC.checkForSoundboards()){
			CSBF = new CreateSoundboardFragment(DC);
			CSBF.show(getFragmentManager(),"CreateSoundboardFragment");
		}
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()){
			case R.id.add_sound:
				ASDF = new AddSoundDialogFragment(DC);
				ASDF.show(getFragmentManager(),"AddSoundDialogFragment");
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
		mfragment.refreshListView();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog){}
}
