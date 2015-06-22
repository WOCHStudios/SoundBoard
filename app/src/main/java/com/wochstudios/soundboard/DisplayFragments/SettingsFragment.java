package com.wochstudios.soundboard.DisplayFragments;

import android.preference.PreferenceFragment;
import android.os.*;

import com.wochstudios.soundboard.R;

public class SettingsFragment extends PreferenceFragment{
	public SettingsFragment()
	{}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	
}
