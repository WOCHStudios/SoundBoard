package com.wochstudios.soundboard.ClickListeners;

import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.wochstudios.soundboard.MainActivityController;
import com.wochstudios.soundboard.R;
import android.widget.AdapterView.*;
import android.widget.*;
import android.view.*;
import android.widget.PopupMenu.*;
import android.util.*;

public class DrawerOnItemClickListener implements OnItemClickListener, OnItemLongClickListener, OnMenuItemClickListener
{

	private DrawerLayout drawer;
	private MainActivityController helper;
	private String soundboardId;
	
	private static final String CURRENT_SOUNDBOARD="currentSoundboard";
	
	public DrawerOnItemClickListener(DrawerLayout dl, MainActivityController mh){
		drawer = dl;
		helper = mh;
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Log.i("DrawerClickListener","Title being searched for: "+(String)parent.getItemAtPosition(position));
		soundboardId = helper.getSoundboardIdFromTitle((String)parent.getItemAtPosition(position));
		
		switch(position) {
			case 0:
				helper.showDialogFragment(helper.CREATE_SOUNDBOARD_FRAGEMENT_CD);
				break;
			default:
				helper.updateSoundboardFragment("" + soundboardId);
				break;
		}
			
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
		soundboardId = helper.getSoundboardIdFromTitle((String)parent.getItemAtPosition(position));
		PopupMenu popup = new PopupMenu(view.getContext(),view);
		popup.setOnMenuItemClickListener(this);
		popup.inflate(R.menu.soundboard_popup_menu);
		popup.show();
		return true;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		switch(item.getItemId()){
			case R.id.remove_soundboard:
				helper.removeSoundboard(soundboardId);
				return true;
			case R.id.rename_soundboard:
				return true;
			default:
				return false;
		}
	}
	
	
	

	
}
