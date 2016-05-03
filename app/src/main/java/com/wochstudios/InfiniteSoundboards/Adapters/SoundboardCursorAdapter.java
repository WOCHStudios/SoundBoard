package com.wochstudios.infinitesoundboards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.wochstudios.infinitesoundboards.R;
import com.wochstudios.infinitesoundboards.database.SoundboardContract;
import com.wochstudios.infinitesoundboards.models.Soundboard;

/**
 * Created by dave on 4/26/2016.
 */
public class SoundboardCursorAdapter extends CursorAdapter {

    public SoundboardCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_item,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView)view;
        textView.setText(cursor.getString(cursor.getColumnIndex(
											SoundboardContract.SoundboardsTable.COLUMN_NAME)));
    }

	public Soundboard getSoundboard(int position){
		Cursor cursor = getCursor();
		Soundboard soundboard = new Soundboard();
		if(cursor.moveToPosition(position)){
			soundboard.setID(cursor.getInt(cursor.getColumnIndex(
											SoundboardContract.SoundboardsTable._ID)));
			soundboard.setTitle(cursor.getString(cursor.getColumnIndex(
											SoundboardContract.SoundboardsTable.COLUMN_NAME)));
			soundboard.setDate_created(cursor.getString(cursor.getColumnIndex(
										SoundboardContract.SoundboardsTable.COLUMN_DATE_CREATED)));

		}
		return soundboard;
	}
}
