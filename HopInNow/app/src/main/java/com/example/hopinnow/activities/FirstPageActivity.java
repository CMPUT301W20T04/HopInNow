package com.example.hopinnow.activities;

import android.annotation.SuppressLint;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.example.hopinnow.R;

//Stackoverflow asked by Anil M H
//https://stackoverflow.com/questions/17357226/add-the-loading-screen-in-starting-of-the-android-application
//answered Jun 28 '13 at 4:38
//MysticMagicϡ
/**
 * Author: Qianxi Li
 * Shows the page before the program begins
 */
public class FirstPageActivity extends AppCompatActivity {
    private ImageView titleImage;
    private ImageView roadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        titleImage = findViewById(R.id.titleImage);
        roadImage = findViewById(R.id.roadImage);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 10 seconds
                } catch (Exception e) {
                } finally {
                    Intent i = new Intent(FirstPageActivity.this,
                            PagerActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();


    }



}
