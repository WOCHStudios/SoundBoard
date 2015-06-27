package com.wochstudios.soundboard.ClickListeners;
import android.widget.AdapterView.*;
import android.widget.*;
import android.view.*;
import android.preference.*;
import android.support.v4.widget.*;
import com.wochstudios.soundboard.*;

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
		PreferenceManager.getDefaultSharedPreferences(view.getContext())
			.edit().putString("currentSoundboard",""+position).commit();
		helper.loadSoundBoardFragment(""+position);
		drawer.closeDrawer(drawer.findViewById(R.id.left_drawer));
			
	}

	
}
