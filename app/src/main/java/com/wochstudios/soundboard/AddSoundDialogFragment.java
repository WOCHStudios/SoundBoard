package com.wochstudios.soundboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.wochstudios.soundboard.Controllers.MapController;
import com.wochstudios.soundboard.Interfaces.IAddSoundDialogListener;

public class AddSoundDialogFragment extends DialogFragment
{
	
	private IAddSoundDialogListener mListener;
	private MapController ASC;
	private View layout;
	private Uri fileUri;

	public AddSoundDialogFragment(MapController c){
		this.ASC =c;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			mListener = (IAddSoundDialogListener) activity;
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
						ASC.AddSoundToMap(title.getText().toString(),fileUri.toString());
						mListener.onDialogPositiveClick(AddSoundDialogFragment.this);
					}
				})
				.setNegativeButton("Cancel", new Dialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						mListener.onDialogNegativeClick(AddSoundDialogFragment.this);
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
				intent.setType("*/*");
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
