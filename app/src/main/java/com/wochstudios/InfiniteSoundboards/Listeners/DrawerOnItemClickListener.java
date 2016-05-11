package com.wochstudios.infinitesoundboards.listeners;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.wochstudios.infinitesoundboards.fragments.RenameDialogFragment;
import com.wochstudios.infinitesoundboards.MainActivityHelper;
import com.wochstudios.infinitesoundboards.models.Soundboard;
import com.wochstudios.infinitesoundboards.R;

public class DrawerOnItemClickListener implements OnItemClickListener, OnItemLongClickListener, OnMenuItemClickListener, View.OnClickListener
{

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
                activityController.removeSoundboard(selectedSoundboard.getID() + "");
                return true;
			case R.id.rename_soundboard:
                //activityController.renameSoundboard("TestRenameFunction", selectedSoundboard.getID()+"");
                //activityController.showDialogFragment(activityController.RENAME_SOUNDBOARD_FRAGMENT_CD, selectedSoundboard.getTitle());
                RenameDialogFragment renameDialogFragment = new RenameDialogFragment();
                renameDialogFragment.setArguments(selectedSoundboard,activityController);
                renameDialogFragment.show(activityController.getFragmentManager(),renameDialogFragment.getClass().getSimpleName());

                return true;
			default:
				return false;
		}
	}


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open_settings:
                Log.d(this.getClass().getSimpleName(),"Open Settings Clicked");
                activityController.startSettingsActivity();
                break;
            case R.id.new_soundboard:
                Log.d(this.getClass().getSimpleName(),"Open New Soundboard clicked");
                activityController.showDialogFragment(MainActivityHelper.CREATE_SOUNDBOARD_FRAGEMENT_CD,"");
                break;
            default:
                break;
        }
    }
}
