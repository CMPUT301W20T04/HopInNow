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

import java.util.Objects;

public class IntroFragment extends Fragment {
    private static final String SectionNumber = "number";
    private int[] drawable_array = new int[]{
      R.drawable.sign_up,
      R.drawable.allow_location,
      R.drawable.click_menu, R.drawable.waiting, R.drawable.payment
    };
    private String[] guide = new String[] {
            "Make sure the user type (rider/driver) when signing up.",
            "Make sure to allow access to the current location.",
            "Check out the menu by clicking the menu button on top left.",
            "While waiting, increase the fare to encourage drivers.",
            "Choose tips, sum up and click confirm to get QR to scan."
    };
    static IntroFragment newInstance(int sectionNumber) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(SectionNumber, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        TextView textView = view.findViewById(R.id.guidePageText);
        String textString = guide[(Objects.requireNonNull(getArguments()).getInt(SectionNumber)-1)];
        textView.setText(textString);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setBackgroundResource(drawable_array[getArguments().getInt(SectionNumber)-1]);
        return view;

    }
}
