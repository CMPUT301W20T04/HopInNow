package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.RequestListAdapter;

import java.util.ArrayList;

public class RequestListFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_driver_requests, container, false);
        ArrayList<Request> requestList = new ArrayList<Request>();
        final FragmentActivity fragmentActivity = getActivity();

        RequestListAdapter adapter = new RequestListAdapter(requestList, fragmentActivity);
        ListView lView = (ListView)fragmentActivity.findViewById(R.id.requestList);
        lView.setAdapter(adapter);


        return view;
    }
}
