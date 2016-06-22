package com.wochstudios.infinitesoundboards;

import android.app.DialogFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wochstudios.infinitesoundboards.adapters.SoundboardAdapter;
import com.wochstudios.infinitesoundboards.adapters.SoundboardCursorAdapter;
import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.fragments.AddSoundDialogFragment;
import com.wochstudios.infinitesoundboards.fragments.CreateSoundboardFragment;
import com.wochstudios.infinitesoundboards.fragments.RenameDialogFragment;
import com.wochstudios.infinitesoundboards.listeners.DrawerOnItemClickListener;
import com.wochstudios.infinitesoundboards.listeners.IDialogListener;
import com.wochstudios.infinitesoundboards.listeners.ISoundboardFragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IDialogListener, ISoundboardFragmentListener{

	public MainActivityHelper mainHelper;

	private Uri soundboardsUri;
	private SoundboardCursorAdapter adapter;
	private ActionBarDrawerToggle toggle;
    private DrawerOnItemClickListener listener;

	@BindView(R.id.container) DrawerLayout drawerLayout;
	@BindView(R.id.left_drawer) ListView drawerList;
	

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ButterKnife.bind(this);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		mainHelper = new MainActivityHelper(this);
        listener = new DrawerOnItemClickListener(drawerLayout, mainHelper);
        setupDrawerLayoutListener(drawerLayout);
		setupDrawerList(drawerList);
		if(savedInstanceState == null){
			mainHelper.initSoundboardFragment();
		}
        setupActionBar();




	}//onCreate
	
	
	private void setupActionBar(){
        if(mainHelper.getCurrentSoundboardTitle() == null ||
                !mainHelper.getCurrentSoundboardId().equals(PreferenceManager.getDefaultSharedPreferences(this).getString("defaultSoundboard", ""))){
			getSupportActionBar().setTitle(R.string.app_name);

        }else {
			getSupportActionBar().setTitle(mainHelper.getCurrentSoundboardTitle());
        }
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		toggle = mainHelper.getToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
		toggle.setDrawerIndicatorEnabled(true);
		toggle.syncState();
	}

	private void setupDrawerList(ListView lv){
		soundboardsUri = SoundboardContract.SoundboardsTable.CONTENT_URI;
		Cursor cursor = getContentResolver().query(soundboardsUri,null,null,null,null);
		adapter = new SoundboardCursorAdapter(this,cursor,0);
        lv.setAdapter(adapter);
		lv.setOnItemClickListener(listener);
		lv.setOnItemLongClickListener(listener);
	}


    private void setupDrawerLayoutListener(DrawerLayout layout){
        layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {
               getSupportActionBar().setTitle("Soundboards");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(mainHelper.getCurrentSoundboardTitle() != null) {
                    getSupportActionBar().setTitle(mainHelper.getCurrentSoundboardTitle());
                }else{
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(toggle.onOptionsItemSelected(item)){
			return true;
		}
		switch (item.getItemId()){
			case R.id.add_new_soundboard:
				mainHelper.showDialogFragment(mainHelper.CREATE_SOUNDBOARD_FRAGEMENT_CD,"");
				return true;
			case R.id.settings:
				mainHelper.startSettingsActivity();
				return true;
			default:	
				return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		if(CreateSoundboardFragment.class.isInstance(dialog)){
			mainHelper.updateDrawerList();
		}else if(AddSoundDialogFragment.class.isInstance(dialog)){
			mainHelper.updateSoundboardFragment(((AddSoundDialogFragment)dialog).getSoundboardID());
		}else if(RenameDialogFragment.class.isInstance(dialog)){
            Toast.makeText(this,"Rename Button Clicked",Toast.LENGTH_SHORT).show();
        }
	}


	@Override
	public void onSoundRemoveCall(String soundID)
	{
		mainHelper.removeSound(soundID);
		mainHelper.updateSoundboardFragment(mainHelper.getCurrentSoundboardId());	
	}


	
	
}
