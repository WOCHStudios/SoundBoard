package com.wochstudios.InfiniteSoundboards.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wochstudios.InfiniteSoundboards.listeners.IDialogListener;
import com.wochstudios.InfiniteSoundboards.MainActivityHelper;
import com.wochstudios.InfiniteSoundboards.models.Soundboard;
import com.wochstudios.InfiniteSoundboards.R;

/**
 * Created by dave on 11/23/2015.
 */
public class RenameDialogFragment extends DialogFragment {


    private IDialogListener listener;
    private View layout;
    private Soundboard soundboard;
    private MainActivityHelper helper;

    public RenameDialogFragment(){}


    public void setArguments(Soundboard sb, MainActivityHelper help){
        this.helper = help;
        this.soundboard = sb;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (IDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.setRetainInstance(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        layout = inflater.inflate(R.layout.rename_dialog_layout,null);
        final EditText rename_edittext = (EditText) layout.findViewById(R.id.rename_textbox);
        rename_edittext.setText(soundboard.getTitle());
        builder.setView(layout)
            .setPositiveButton("Rename", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(!rename_edittext.getText().toString().isEmpty()){
                        helper.renameSoundboard(rename_edittext.getText().toString(), soundboard.getID() + "");
                        helper.updateDrawerList();
                    }
                }
            })
            .setNegativeButton("Cancel", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .setTitle("Rename Soundboard");
        return builder.create();

    }
}
