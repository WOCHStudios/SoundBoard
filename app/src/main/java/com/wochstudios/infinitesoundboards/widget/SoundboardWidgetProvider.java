package com.wochstudios.infinitesoundboards.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.wochstudios.infinitesoundboards.R;
import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.service.MediaPlayerService;
import com.wochstudios.infinitesoundboards.service.SoundboardWidgetService;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by dave on 5/5/2016.
 */


public class SoundboardWidgetProvider extends AppWidgetProvider{

	public static final String PLAY_ACTION = "com.wochstudios.inifinitesoundboards.sounboardwidget.PLAY_ACTION";
	public static final String EXTRA_SOUND_ID = "com.wochstudios.inifinitesoundboards.sounboardwidget.EXTRA_SOUND_ID";
	public static final String EXTRA_SOUND_URI = "com.wochstudios.inifinitesoundboards.sounboardwidget.EXTRA_SOUND_URI";
	public static final String FROM_WIDGET ="FROM_WIDGET";




	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(this.getClass().getSimpleName(),"onRecive() called");
		AppWidgetManager mgr = AppWidgetManager.getInstance(context);
		if (intent.getAction().equals(PLAY_ACTION)) {
			int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			//int soundId = intent.getIntExtra(EXTRA_SOUND_ID, 0);
			//Toast.makeText(context, "Sound ID" + soundId, Toast.LENGTH_SHORT).show();
			String soundUri = intent.getStringExtra(EXTRA_SOUND_URI);
			Log.d(getClass().getSimpleName(),"Uri: "+soundUri);
			Intent mediaPlayerIntent = new Intent(context, MediaPlayerService.class);
			mediaPlayerIntent.putExtra(FROM_WIDGET,FROM_WIDGET);
			mediaPlayerIntent.putExtra(MediaPlayerService.soundUri,soundUri);
			context.startService(mediaPlayerIntent);


		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		ComponentName thisWidget = new ComponentName(context,SoundboardWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for(int widgetId : allWidgetIds){
			RemoteViews remoteViews = updateWidgetListView(context,widgetId);

			Intent touchIntent = new Intent(context,SoundboardWidgetProvider.class);
			touchIntent.setAction(PLAY_ACTION);
			touchIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
			touchIntent.setData(Uri.parse(touchIntent.toUri(Intent.URI_INTENT_SCHEME)));
			PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context,0,touchIntent,
																PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setPendingIntentTemplate(R.id.widget_sounds_list, toastPendingIntent);

			appWidgetManager.updateAppWidget(widgetId,remoteViews);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);

	}

	private RemoteViews updateWidgetListView(Context con, int widgetId){
		RemoteViews remoteViews = new RemoteViews(con.getPackageName(), R.layout.widget_layout);
		remoteViews.setTextViewText(R.id.widget_header,con.getText(R.string.app_name));
		Intent intent = new Intent(con, SoundboardWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
		remoteViews.setRemoteAdapter(R.id.widget_sounds_list, intent);
		remoteViews.setEmptyView(R.id.widget_sounds_list, R.id.widget_emptyview);
		return remoteViews;
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

}
