package com.wochstudios.soundboard.Listeners;

import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.wochstudios.soundboard.MainActivityHelper;
import com.wochstudios.soundboard.Models.Soundboard;
import com.wochstudios.soundboard.R;

public class DrawerOnItemClickListener implements OnItemClickListener, OnItemLongClickListener, OnMenuItemClickListener
{

    private static final String CURRENT_SOUNDBOARD = "currentSoundboard";
    private DrawerLayout drawer;
    private MainActivityHelper activityController;
    private Soundboard selectedSoundboard;
	
	public DrawerOnItemClickListener(DrawerLayout dl, MainActivityHelper mh){
		drawer = dl;
        activityController = mh;
    }
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
        selectedSoundboard = (Soundboard) parent.getItemAtPosition(position);
        activityController.updateSoundboardFragment(selectedSoundboard.getID() + "");
        drawer.closeDrawers();
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
        selectedSoundboard = (Soundboard)parent.getItemAtPosition(position);
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
                activityController.removeSoundboard(selectedSoundboard.getID()+"");
                return true;
			case R.id.rename_soundboard:
                activityController.renameSoundboard("TestRenameFunction", selectedSoundboard.getID()+"");
                return true;
			default:
				return false;
		}
	}
	
	
	

	
}
