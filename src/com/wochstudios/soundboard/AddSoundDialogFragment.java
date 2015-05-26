package com.wochstudios.soundboard;
import android.app.*;
import android.os.*;
import android.content.*;
import android.text.*;
import android.view.*;

public class AddSoundDialogFragment extends DialogFragment
{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.test_layout,null))
				.setPositiveButton("Fire", new Dialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						
					}
				})
				.setNegativeButton("Cancel", new Dialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
					
					}
				});
		return builder.create();
	}
	
}
