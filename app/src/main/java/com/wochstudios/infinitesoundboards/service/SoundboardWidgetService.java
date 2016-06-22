package com.wochstudios.InfiniteSoundboards.service;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.wochstudios.InfiniteSoundboards.widget.SoundboardWidgetListProvider;

/**
 * Created by dave on 5/5/2016.
 */
public class SoundboardWidgetService extends RemoteViewsService {
	private SoundboardWidgetListProvider listProvider;

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
		listProvider = new SoundboardWidgetListProvider(this.getApplicationContext(),intent);
		return listProvider;
	}
}
