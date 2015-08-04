package com.wochstudios.soundboard.ClickListeners;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.wochstudios.soundboard.MainActivityController;
import com.wochstudios.soundboard.R;

public class DrawerOnItemClickListener implements OnItemClickListener, OnItemLongClickListener, OnMenuItemClickListener
{

    private static final String CURRENT_SOUNDBOARD = "currentSoundboard";
    private DrawerLayout drawer;
    private MainActivityController activityController;
    private String soundboardId;
	
	public DrawerOnItemClickListener(DrawerLayout dl, MainActivityController mh){
		drawer = dl;
        activityController = mh;
    }
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
        Log.i("DrawerClickListener", "Title being searched for: " + parent.getItemAtPosition(position));
        soundboardId = activityController.getSoundboardIdFromTitle((String) parent.getItemAtPosition(position));

        switch(position) {
			case 0:
                activityController.showDialogFragment(MainActivityController.CREATE_SOUNDBOARD_FRAGEMENT_CD);
                break;
			default:
                activityController.updateSoundboardFragment("" + soundboardId);
                break;
		}
			
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
        soundboardId = activityController.getSoundboardIdFromTitle((String) parent.getItemAtPosition(position));
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
                activityController.removeSoundboard(soundboardId);
                return true;
			case R.id.rename_soundboard:
                activityController.renameSoundboard("TestRenameFunction", soundboardId);
                return true;
			default:
				return false;
		}
	}
	
	
	

	
}
