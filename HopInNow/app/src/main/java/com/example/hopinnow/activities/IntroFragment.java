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

public class IntroFragment extends Fragment {
    private ImageView imageView;
    private static final String SectionNumber = "number";
    private int[] drawable_array = new int[]{
      R.drawable.shway,
      R.drawable.shway2,
      R.drawable.shway3, R.drawable.shway4, R.drawable.zhiqi
    };
    public static IntroFragment newInstance(int sectionNumber) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(SectionNumber, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        TextView textView = view.findViewById(R.id.guidePageText);
        String textString = "Page "+(getArguments().getInt(SectionNumber)-1);
        textView.setText(textString);
        imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setBackgroundResource(drawable_array[getArguments().getInt(SectionNumber)-1]);
        return view;

    }
}
