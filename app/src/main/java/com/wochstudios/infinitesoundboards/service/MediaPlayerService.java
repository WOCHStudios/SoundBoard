package com.wochstudios.infinitesoundboards.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by dave on 5/2/2016.
 */
public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener {

	private final IBinder binder = new LocalBinder();
	private MediaPlayer mediaPlayer = new MediaPlayer();

	public static final String soundUri = "soundUri";
	public boolean complete = false;

	public MediaPlayerService(){}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(!mediaPlayer.isPlaying()){
			Log.d(this.getClass().getSimpleName(), "Service Started Successfully");
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
		mediaPlayer.release();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		complete = true;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}


	public void startMediaPlayer(){
		if(!mediaPlayer.isPlaying()){
			mediaPlayer.start();
		}
	}


	public void setupMediaPlayer(Intent intent) throws IOException{
		if(!mediaPlayer.isPlaying()){
			Uri uri = Uri.parse(intent.getStringExtra(soundUri));
			mediaPlayer.setDataSource(getContentResolver()
										.openAssetFileDescriptor(uri,"r").getFileDescriptor());
			mediaPlayer.prepare();
		}

	}

	public class LocalBinder extends Binder{
		public MediaPlayerService getService(){
			return MediaPlayerService.this;
		}
	}
}
