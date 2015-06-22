package com.wochstudios.soundboard.DisplayFragments;

import android.app.*;
import android.view.*;
import android.os.*;
import android.content.DialogInterface;

import com.wochstudios.soundboard.R;
import com.wochstudios.soundboard.Controllers.*;
import android.widget.*;

public class CreateSoundboardFragment extends DialogFragment
{
	private View layout;
	private DatabaseController DC;
	
	public CreateSoundboardFragment(DatabaseController dc)
	{
		this.DC = dc;
	}

	@Override
	public void onAttach(Activity activity)
	{
		// TODO: Implement this method
		super.onAttach(activity);
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
				}
			})
			.setTitle("Create Soundboard");
		return builder.create();
	}

		
	
}
