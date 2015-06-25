package com.wochstudios.soundboard.DisplayFragments;

import android.app.*;
import android.view.*;
import android.os.*;
import android.content.DialogInterface;

import com.wochstudios.soundboard.R;
import com.wochstudios.soundboard.Controllers.*;
import android.widget.*;
import com.wochstudios.soundboard.Interfaces.*;

public class CreateSoundboardFragment extends DialogFragment
{
	private View layout;
	private DatabaseController DC;
	private IDialogListener mListener;
	
	public CreateSoundboardFragment(DatabaseController dc)
	{
		this.DC = dc;
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
		AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		layout = inflater.inflate(R.layout.create_soundboard_layout, null);
		builder.setView(layout)
			.setPositiveButton("Create Soundboard", new Dialog.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					EditText text = (EditText)layout.findViewById(R.id.SoundBoardTitle);
					DC.addSoundboardToDatabase(text.getText().toString());
					mListener.onDialogPositiveClick(CreateSoundboardFragment.this);
				}
			})
			.setTitle("Create Soundboard");
		return builder.create();
	}

		
	
}
