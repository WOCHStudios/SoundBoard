package com.wochstudios.InfiniteSoundboards;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.wochstudios.InfiniteSoundboards.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(true);

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
