package com.wochstudios.soundboard;
import android.app.*;
import android.os.*;
import android.content.*;
import android.text.*;
import android.view.*;

import com.wochstudios.soundboard.Interfaces.AddSoundDialogListener;

public class AddSoundDialogFragment extends DialogFragment
{
	private AddSoundController ASC;
	
	private AddSoundDialogListener mListener;

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
		builder.setView(inflater.inflate(R.layout.test_layout,null))
				.setPositiveButton("Add", new Dialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						mListener.onDialogPositiveClick(AddSoundDialogFragment.this);
					}
				})
				.setNegativeButton("Cancel", new Dialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						mListener.onDialogNegativeClick(AddSoundDialogFragment.this);
					}
				})
				.setTitle("Add Sound");
		return builder.create();
	}
	
}
