package com.wochstudios.soundboard.ClickListeners;

import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.wochstudios.soundboard.MainActivityHelper;
import com.wochstudios.soundboard.R;

public class DrawerOnItemClickListener implements OnItemClickListener
{
	private DrawerLayout drawer;
	private MainActivityHelper helper;
	
	public DrawerOnItemClickListener(DrawerLayout dl, MainActivityHelper mh){
		drawer = dl;
		helper = mh;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		switch(position) {
			case 0:
				helper.showDialogFragment(helper.CREATE_SOUNDBOARD_FRAGEMENT_CD);
				break;
			default:
				PreferenceManager.getDefaultSharedPreferences(view.getContext())
						.edit().putString("currentSoundboard", "" + position).commit();
				helper.loadSoundBoardFragment("" + position);
				drawer.closeDrawer(drawer.findViewById(R.id.left_drawer));
				break;
		}
			
	}

	
}
