package com.wochstudios.infinitesoundboards.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.wochstudios.infinitesoundboards.database.SoundboardContract;

/**
 * Created by dave on 5/5/2016.
 */
public class SoundboardWidgetListProvider implements RemoteViewsService.RemoteViewsFactory, Loader.OnLoadCompleteListener<Cursor> {

	private Context context;
	private int appWidgetId;


	public SoundboardWidgetListProvider(Context con, Intent intent) {
		this.context = con;
		String defualtSoundboard = PreferenceManager.getDefaultSharedPreferences(con).getString("defaultSoundboard", "");
		this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		CursorLoader loader = new CursorLoader(context,
				SoundboardContract.SoundsTable.buildSoundsFromSoundboardUri(defualtSoundboard)
				,null
				,null
				,null
				,null);
		loader.registerListener(0,this);
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
		return 0;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		return null;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}


	@Override
	public void onLoadComplete(Loader<Cursor> loader, Cursor data) {

	}
}
