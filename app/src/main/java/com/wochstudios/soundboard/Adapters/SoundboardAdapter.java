package com.wochstudios.soundboard.Adapters;

import android.widget.*;
import android.content.Context;

import java.util.List;

import com.wochstudios.soundboard.Models.*;
import android.view.*;

public class SoundboardAdapter extends ArrayAdapter<Soundboard>
{
	public SoundboardAdapter(Context context, List<Soundboard> objects){
		super(context,0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return super.getView(position, convertView, parent);
	}
	
	
	
	
	
}
