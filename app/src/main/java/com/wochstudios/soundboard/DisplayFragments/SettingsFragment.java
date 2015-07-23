package com.wochstudios.soundboard.DisplayFragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wochstudios.soundboard.R;
import android.preference.*;
import java.util.*;
import android.view.*;

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
		// TODO: Implement this method
		ListPreference listPref = (ListPreference) findPreference("defaultSoundboard");
		listPref.setEntries(soundboardEntries.toArray(new CharSequence[soundboardEntries.size()]));
		listPref.setEntryValues(soundboardValues.toArray(new CharSequence[soundboardValues.size()]));
		listPref.setDefaultValue("1");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	
	
}
