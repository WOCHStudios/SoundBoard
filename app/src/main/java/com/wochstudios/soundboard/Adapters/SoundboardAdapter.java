package com.wochstudios.soundboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wochstudios.soundboard.Models.Soundboard;
import com.wochstudios.soundboard.R;

import java.util.List;

public class SoundboardAdapter extends ArrayAdapter<Soundboard>
{
    private  ViewHolder viewHolder;

    public SoundboardAdapter(Context context, List<Soundboard> objects){
        super(context, 0, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
        Soundboard soundboard = getItem(position);

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder.soundboardTitle = (TextView) convertView.findViewById(R.id.listItemTxt);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


        viewHolder.soundboardTitle.setText(soundboard.getTitle());

        return convertView;
	}

    private class ViewHolder{
        TextView soundboardTitle;
    }
	
	
}
