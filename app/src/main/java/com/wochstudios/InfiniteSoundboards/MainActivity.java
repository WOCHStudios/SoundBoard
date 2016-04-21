package com.wochstudios.InfiniteSoundboards;

import android.app.DialogFragment;
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

import com.wochstudios.InfiniteSoundboards.Adapters.SoundboardAdapter;
import com.wochstudios.InfiniteSoundboards.Fragments.AddSoundDialogFragment;
import com.wochstudios.InfiniteSoundboards.Fragments.CreateSoundboardFragment;
import com.wochstudios.InfiniteSoundboards.Fragments.RenameDialogFragment;
import com.wochstudios.InfiniteSoundboards.Listeners.DrawerOnItemClickListener;
import com.wochstudios.InfiniteSoundboards.Listeners.IDialogListener;
import com.wochstudios.InfiniteSoundboards.Listeners.ISoundboardFragmentListener;

public class MainActivity extends AppCompatActivity implements IDialogListener, ISoundboardFragmentListener{

	public MainActivityHelper mainHelper;
	
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle toggle;
    private DrawerOnItemClickListener listener;
	

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		mainHelper = new MainActivityHelper(this);
		//mainHelper.checkForSoundboards();
		drawerLayout = (DrawerLayout)findViewById(R.id.container);
        listener = new DrawerOnItemClickListener(drawerLayout, mainHelper);
        setupDrawerLayoutListener(drawerLayout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		setupDrawerList(drawerList);
        setupDrawerButtons();
		if(savedInstanceState == null){
			mainHelper.initSoundboardFragment();
		}
		Log.d("ACTIONBAR IS NULL?", (getSupportActionBar() != null)+"");
        setupActionBar();



	}//onCreate
	
	
	private void setupActionBar(){

        if(mainHelper.getCurrentSoundboardTitle() == null ||
                !mainHelper.getCurrentSoundboardId().equals(PreferenceManager.getDefaultSharedPreferences(this).getString("defaultSoundboard", ""))){
			getSupportActionBar().setTitle(R.string.app_name);

        }else {
			getSupportActionBar().setTitle(mainHelper.getCurrentSoundboardTitle());
        }
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_drawer);
		toggle = mainHelper.getToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
	}

	private void setupDrawerList(ListView lv){
        SoundboardAdapter soundboardAdapter = new SoundboardAdapter(this,mainHelper.getDatabaseController().getSoundboards());
        lv.setAdapter(soundboardAdapter);
		lv.setOnItemClickListener(listener);
		lv.setOnItemLongClickListener(listener);
	}


    private void setupDrawerButtons(){
        LinearLayout addSoundboardBtn = (LinearLayout) findViewById(R.id.new_soundboard);
        LinearLayout settingsBtn = (LinearLayout) findViewById(R.id.open_settings);

        addSoundboardBtn.setOnClickListener(listener);
        settingsBtn.setOnClickListener(listener);
    }

    private void setupDrawerLayoutListener(DrawerLayout layout){
        layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
               getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
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
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_main, menu);
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
