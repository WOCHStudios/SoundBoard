package com.wochstudios.soundboard;

import android.app.Activity;
import android.os.Bundle;

import com.wochstudios.soundboard.DisplayFragments.SettingsFragment;

public class SettingsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		SettingsFragment settingsFrag = new SettingsFragment();
		settingsFrag.setArguments(getIntent().getExtras());
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, settingsFrag)
			.commit();
	}
	
}
