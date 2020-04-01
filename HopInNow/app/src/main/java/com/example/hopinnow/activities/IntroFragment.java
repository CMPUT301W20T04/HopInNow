package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
//https://guides.codepath.com/android/viewpager-with-fragmentpageradapter

/**
 * Author: Qianxi Li
 * This is introduction page for the user.
 * state the content of the activity
 */
public class IntroFragment extends Fragment {
    private ImageView imageView;
    private static final String SectionNumber = "number";
    private int[] drawable_array = new int[]{
      R.drawable.shway,
      R.drawable.shway2,
      R.drawable.shway3, R.drawable.shway4, R.drawable.zhiqi
    };

    /**
     * initralize the Introfragment with certain sectionNumber
     * @param sectionNumber
     * @return a fragment
     */
    public static IntroFragment newInstance(int sectionNumber) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(SectionNumber, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * create view for the IntroFragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return a view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setBackgroundResource(drawable_array[getArguments().getInt(SectionNumber)-1]);
        return view;

    }
}
