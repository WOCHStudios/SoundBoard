package com.wochstudios.infinitesoundboards.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.wochstudios.infinitesoundboards.R;
import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.models.Sound;

import java.util.ArrayList;

/**
 * Created by dave on 5/5/2016.
 */
public class SoundboardWidgetListProvider implements RemoteViewsService.RemoteViewsFactory, Loader.OnLoadCompleteListener<Cursor> {

	private Context context;
	private int appWidgetId;
	private boolean soundsLoaded = false;
	private ArrayList testList = new ArrayList();
	private ArrayList<Sound> soundList = new ArrayList<>();


	public SoundboardWidgetListProvider(Context con, Intent intent) {
		Log.d(this.getClass().getSimpleName(),"New List Provider created.");
		this.context = con;
		String defualtSoundboard = PreferenceManager.getDefaultSharedPreferences(con).getString("defaultSoundboard", "");
		Log.d(this.getClass().getSimpleName(),"Default Soundboard Id: "+defualtSoundboard);
		this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		CursorLoader loader = new CursorLoader(context,
				SoundboardContract.SoundsTable.buildSoundsFromSoundboardUri(defualtSoundboard)
				,null
				,null
				,null
				,null);
		loader.registerListener(0,this);
		loader.startLoading();
		for(int i = 0; i <10 ;i++){
			testList.add("Test Content");
		}
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		while(!soundsLoaded){
		}
		Log.d(this.getClass().getSimpleName(),"Sounds loaded");
		return soundList.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		Log.d(this.getClass().getSimpleName(),"getViewAt() call");
		final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_item);
		remoteView.setTextViewText(R.id.widget_list_item,soundList.get(position).getTitle());

		Bundle extras = new Bundle();
		extras.putInt(SoundboardWidgetProvider.EXTRA_SOUND_ID,soundList.get(position).getID());
		extras.putString(SoundboardWidgetProvider.EXTRA_SOUND_URI,soundList.get(position).getUri().toString());
		Intent fillIntent = new Intent();
		fillIntent.putExtras(extras);

		remoteView.setOnClickFillInIntent(R.id.widget_list_item,fillIntent);
		return remoteView;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}


	@Override
	public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
		data.moveToFirst();
		Log.d(this.getClass().getSimpleName(),"Cursor size:"+data.getCount());
		while(!data.isAfterLast()){
			Sound sound = new Sound();
			sound.setID(data.getInt(data.getColumnIndex(SoundboardContract.SoundsTable._ID)));
			sound.setTitle(data.getString(data.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_TITLE)));
			sound.setSoundboardId(data.getString(data.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID)));
			sound.setUri(Uri.parse(data.getString(data.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_URI))));
			soundList.add(sound);
			data.moveToNext();
		}
		Log.d(this.getClass().getSimpleName(),"Sounds loaded into list");
		data.close();
		soundsLoaded =true;
	}
}
