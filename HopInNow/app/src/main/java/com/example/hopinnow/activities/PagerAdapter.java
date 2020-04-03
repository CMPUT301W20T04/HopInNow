package com.example.hopinnow.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Author: Qianxi Li
 *
 */
public class PagerAdapter extends FragmentPagerAdapter {
    PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return IntroFragment.newInstance(position+1);
    }
    /**
     * Get the amount of pages
     * @return 5
     */
    @Override
    public int getCount() {
        return 5;
    }
}
