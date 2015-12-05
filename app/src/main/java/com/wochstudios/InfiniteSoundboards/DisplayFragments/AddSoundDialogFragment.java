package com.wochstudios.InfiniteSoundboards.DisplayFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wochstudios.InfiniteSoundboards.Controllers.DatabaseController;
import com.wochstudios.InfiniteSoundboards.Listeners.IDialogListener;
import com.wochstudios.InfiniteSoundboards.R;

public class AddSoundDialogFragment extends DialogFragment
{
	
	private IDialogListener mListener;
	private DatabaseController DC;
	
	private View layout;
	private Uri fileUri;
	private String soundboard_id;
    private Intent intent;

    public AddSoundDialogFragment(){}

    public void setArguments(DatabaseController dc, String soundboard_id){
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
        this.setRetainInstance(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		layout = inflater.inflate(R.layout.dialog_layout,null);
		builder.setView(layout)
				.setPositiveButton("Add", new Dialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText title = (EditText) layout.findViewById(R.id.SoundTitle);
                        if (fileUri != null) {
                            DC.addSoundToSoundboard(title.getText().toString(), fileUri.toString(), soundboard_id);
                            mListener.onDialogPositiveClick(AddSoundDialogFragment.this);
                        } else {
                            Toast.makeText(getActivity(),"Cannot add sound without file",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
				.setTitle("Add Sound");
		setupFileBrowserButton(layout);
				
		return builder.create();
	}
	
	private void setupFileBrowserButton(View v){
		Button browse = (Button)v.findViewById(R.id.BrowseBtn);
		browse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFileBrowser();
            }
        });
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == 1002 && resultCode == Activity.RESULT_OK){
			if(data != null){
                final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
				fileUri = data.getData();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ContentResolver resolver = getActivity().getContentResolver();
                    resolver.takePersistableUriPermission(fileUri,takeFlags);
                }
                updateFilePathField(fileUri);
			}
		}
	}
	
	private void updateFilePathField(Uri fileUri){
		EditText filePathField = (EditText) layout.findViewById(R.id.SoundFile);
		filePathField.setText(fileUri.getPath());
	}

    private void launchFileBrowser(){
        final int KITKAT_VALUE =1002;
        if (Build.VERSION.SDK_INT < 19){
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("audio/*");
        startActivityForResult(intent, KITKAT_VALUE);
    }
	
}
