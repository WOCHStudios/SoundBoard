package com.wochstudios.soundboard;
import android.app.*;
import android.os.*;
import android.content.*;
import android.text.*;
import android.view.*;
import android.widget.Button;

import com.wochstudios.soundboard.Interfaces.AddSoundDialogListener;
import android.view.View.*;
import android.widget.*;
import android.net.Uri;

public class AddSoundDialogFragment extends DialogFragment
{
	
	private AddSoundDialogListener mListener;
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
			mListener = (AddSoundDialogListener) activity;
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
