package com.example.hopinnow.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    /**
     * Get item according to the position
     * and return the item
     */
    public Fragment getItem(int position) {
        return IntroFragment.newInstance(position+1);
    }
    /**
     * Get the count of the guide pages
     * @return 5
     */
    @Override
    public int getCount() {
        return 5;
    }
}
