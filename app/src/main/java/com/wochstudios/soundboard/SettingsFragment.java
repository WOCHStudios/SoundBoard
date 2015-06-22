package com.wochstudios.soundboard;
import android.preference.PreferenceFragment;
import android.os.*;

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
