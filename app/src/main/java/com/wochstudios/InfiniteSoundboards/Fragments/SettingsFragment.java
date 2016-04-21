package com.wochstudios.InfiniteSoundboards.Fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wochstudios.InfiniteSoundboards.R;

import java.util.ArrayList;

public class SettingsFragment extends PreferenceFragment{
	
	private ArrayList<String> soundboardEntries;
	private ArrayList<String> soundboardValues;
	
	public SettingsFragment()
	{}

	@Override
	public void setArguments(Bundle args)
	{
		// TODO: Implement this method
		super.setArguments(args);
		soundboardEntries = args.getStringArrayList("SOUNDBOARD_NAMES");
		soundboardValues = args.getStringArrayList("SOUNDBOARD_IDS");
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        this.setRetainInstance(true);
		// TODO: Implement this method
		ListPreference listPref = (ListPreference) findPreference("defaultSoundboard");
		listPref.setEntries(soundboardEntries.toArray(new CharSequence[soundboardEntries.size()]));
		listPref.setEntryValues(soundboardValues.toArray(new CharSequence[soundboardValues.size()]));
		listPref.setDefaultValue("1");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	
	
}
