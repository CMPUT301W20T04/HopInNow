package com.example.hopinnow.helperclasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.hopinnow.R;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Sets a progress bar on the top of the screen
 */
public class ProgressbarDialog {
    private Activity activity;
    private AlertDialog alertDialog;
    private ViewGroup viewGroup;

    public ProgressbarDialog(Activity activity, ViewGroup viewGroup) {
        this.activity = activity;
        this.viewGroup = viewGroup;
    }

    public void startProgressbarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        LayoutInflater inflater = this.activity.getLayoutInflater();
        inflater.inflate(R.layout.custom_progress_bar, this.viewGroup, false);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog() {
        if ((!this.activity.isFinishing())&&(alertDialog!=null)){
            alertDialog.dismiss();
        }

    }
}
