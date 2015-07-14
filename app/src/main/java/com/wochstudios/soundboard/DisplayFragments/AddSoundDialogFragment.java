package com.wochstudios.soundboard.DisplayFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.wochstudios.soundboard.Controllers.DatabaseController;
import com.wochstudios.soundboard.Interfaces.IDialogListener;
import com.wochstudios.soundboard.R;

public class AddSoundDialogFragment extends DialogFragment
{
	
	private IDialogListener mListener;
	private DatabaseController DC;
	
	private View layout;
	private Uri fileUri;
	private String soundboard_id;

	public AddSoundDialogFragment(DatabaseController dc, String soundboard_id){
		this.DC = dc;
		this.soundboard_id = soundboard_id;
	}
	
	public String getSoundboardID(){
		return this.soundboard_id;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			mListener = (IDialogListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.toString()+" must implement Listener");
		}
	}
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		layout = inflater.inflate(R.layout.dialog_layout,null);
		builder.setView(layout)
				.setPositiveButton("Add", new Dialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						EditText title = (EditText) layout.findViewById(R.id.SoundTitle);
						Log.d("","");
						DC.addSoundToSoundboard(title.getText().toString(),fileUri.toString(),soundboard_id);
						mListener.onDialogPositiveClick(AddSoundDialogFragment.this);
					}
				})
				.setTitle("Add Sound");
		setupFileBrowserButton(layout);
				
		return builder.create();
	}
	
	private void setupFileBrowserButton(View v){
		Button browse = (Button)v.findViewById(R.id.BrowseBtn);
		browse.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("audio/*");
				startActivityForResult(intent,42);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == 42 && resultCode == Activity.RESULT_OK){
			if(data != null){
				fileUri = data.getData();
				updateFilePathField(fileUri);
			}
		}
	}
	
	private void updateFilePathField(Uri fileUri){
		EditText filePathField = (EditText) layout.findViewById(R.id.SoundFile);
		filePathField.setText(fileUri.getPath());
	}
	
}
