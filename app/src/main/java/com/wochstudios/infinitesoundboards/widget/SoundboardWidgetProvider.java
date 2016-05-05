package com.wochstudios.infinitesoundboards.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.widget.RemoteViews;

import com.wochstudios.infinitesoundboards.database.SoundboardContract;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by dave on 5/5/2016.
 */


public class SoundboardWidgetProvider extends AppWidgetProvider implements PreferenceChangeListener {

	private RemoteViews updateWidgetListView(){
		return null;
	}


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void preferenceChange(PreferenceChangeEvent pce) {

	}
}
