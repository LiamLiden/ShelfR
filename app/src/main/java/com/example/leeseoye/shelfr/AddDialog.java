package com.example.leeseoye.shelfr;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDialog extends DialogFragment {
    static EditText editText;
    boolean positiive = false;
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick();
        public void onDialogNegativeClick();
        public void onDialogNeutralClick();
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(this.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // onAttach(getActivity());
        //editText = getActivity().findViewById(R.id.text_box);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(R.layout.dialog_add)
                // Add action buttons
                .setPositiveButton("Remove One", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNeutralClick();
                    }
                });
        Dialog dia = builder.create();
//        if(positiive){
//            String txt = ((EditText) getActivity().findViewById(R.id.text_box)).getText().toString();
//            Toast.makeText(getActivity().getApplicationContext(), "HERE", Toast.LENGTH_LONG).show();
//
//            mListener.onDialogPositiveClick(txt);
//        }
        return dia;

    }
}
