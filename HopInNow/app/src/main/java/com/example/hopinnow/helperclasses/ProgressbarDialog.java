package com.example.hopinnow.helperclasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;

import com.example.hopinnow.R;

import java.util.Objects;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Sets a progress bar on the top of the screen
 */
public class ProgressbarDialog {
    private Context context;
    //private ViewGroup viewGroup;
    private Dialog dialog;

    public ProgressbarDialog(Context context) {
        this.context = context;
    }

    public void startProgressbarDialog() {
        this.dialog = new Dialog(this.context,
                android.R.style.Theme_Translucent_NoTitleBar);
        // Setting dialogview
        Window window = dialog.getWindow();
        Objects.requireNonNull(window).setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog!=null && dialog.isShowing()){
            this.dialog.dismiss();
        }

    }

}
