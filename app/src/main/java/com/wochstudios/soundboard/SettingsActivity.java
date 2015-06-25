package com.wochstudios.soundboard;

import android.app.Activity;
import android.os.*;

import com.wochstudios.soundboard.DisplayFragments.SettingsFragment;

public class SettingsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new SettingsFragment())
			.commit();
	}
	
}
