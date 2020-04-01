package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.hopinnow.R;
import com.example.hopinnow.helperclasses.SharedPreference;

//https://guides.codepath.com/android/viewpager-with-fragmentpageradapter
public class PagerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button finishButton;
    private Button nextPageButton;
    private PagerAdapter PagerAdapter;
    private ViewPager viewPager;
    private ImageView[] switchers;
    private ImageView switcher0;
    private ImageView switcher1;
    private ImageView switcher2;
    private ImageView switcher3;
    private ImageView switcher4;
    private boolean whether_first_use;
    private int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view to full screen
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
        setContentView(R.layout.activity_pager);
        // Enter the App for the first time and show the guide page
        whether_first_use = SharedPreference.readSetting(PagerActivity.this, false,"page_settings");

        if (whether_first_use) {
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        viewPager = findViewById(R.id.container);
        switcher0 = findViewById(R.id.switch_0);
        switcher1 = findViewById(R.id.switch_1);
        switcher2 = findViewById(R.id.switch_2);
        switcher3 = findViewById(R.id.switch_3);
        switcher4 = findViewById(R.id.switch_4);
        finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(this);
        nextPageButton = findViewById(R.id.nextButton);
        nextPageButton.setOnClickListener(this);
        initializeViewPager();
    }
    /**
     * Gets the click status of each button
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finishButton:
                SharedPreference.saveSetting(PagerActivity.this,true,"page_settings");
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.nextButton:
                currentPage+=1;
                if(currentPage>4){
                    currentPage = 4;
                }
                viewPager.setCurrentItem(currentPage,true);
                break;
            default:
                break;
        }
    }
    public void initializeViewPager(){
        PagerAdapter = new PagerAdapter(getSupportFragmentManager());
        nextPageButton = findViewById(R.id.nextButton);
        switchers = new ImageView[]{
                switcher0,switcher1,switcher2,switcher3,switcher4
        };

        viewPager.setAdapter(PagerAdapter);
        viewPager.setCurrentItem(currentPage);
        updateSwitcher(currentPage);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
                updateSwitcher(currentPage);
                if (position == 4){
                    finishButton.setVisibility(View.VISIBLE);
                    nextPageButton.setVisibility(View.GONE);
                }
                else{
                    finishButton.setVisibility(View.GONE);
                    nextPageButton.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                updateSwitcher(currentPage);
                if (position == 4){
                    finishButton.setVisibility(View.VISIBLE);
                    nextPageButton.setVisibility(View.GONE);
                }
                else{
                    finishButton.setVisibility(View.GONE);
                    nextPageButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public void updateSwitcher(int index){
        for(int i=0;i<switchers.length;i++){
            if(i == index){
                switchers[i].setBackgroundResource(R.drawable.pager_point);
            }
            else{
                switchers[i].setBackgroundResource(R.drawable.pager_point_inactive);
            }


        }

    }
}

