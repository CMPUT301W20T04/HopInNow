package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hopinnow.R;

public class RiderDriverOfferFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_rider_location_search, container);

        // Get Fragment belonged Activity
        final FragmentActivity fragmentActivity = getActivity();

        if(retView!=null)
        {
            // Click this button will show the text in right fragment.
            Button androidButton = retView.findViewById(R.id.fragmentLeftButtonAndroid);
            androidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        return retView;
    }
}
