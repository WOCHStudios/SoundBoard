package com.wochstudios.soundboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.wochstudios.soundboard.DisplayFragments.SettingsFragment;

public class SettingsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        getActionBar().setHomeButtonEnabled(true);

		SettingsFragment settingsFrag = new SettingsFragment();
		settingsFrag.setArguments(getIntent().getExtras());
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, settingsFrag)
			.commit();
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
}
