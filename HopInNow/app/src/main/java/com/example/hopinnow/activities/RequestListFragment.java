package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
    Integer prePosition;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_driver_requests, container, false);
        ArrayList<Request> requestList = new ArrayList<Request>();
        final FragmentActivity fragmentActivity = getActivity();

        RequestListAdapter adapter = new RequestListAdapter(requestList, fragmentActivity);
        final ListView requestListView = (ListView)fragmentActivity.findViewById(R.id.requestList);
        requestListView.setAdapter(adapter);

        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View itemView = getViewByPosition(position, requestListView);
                Button acceptBtn = itemView.findViewById(R.id.accept_btn);
                acceptBtn.setVisibility(View.VISIBLE);

                if (prePosition != null){
                    Button preAcceptBtn = getViewByPosition(position, requestListView).findViewById(R.id.accept_btn);
                    preAcceptBtn.setVisibility(View.GONE);
                }
                acceptBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                    }
                });


            }
        });


        return view;
    }

    /*
    Citation:
    Author: VVB
    Date: Jul 21 '14 at 11:57
    Title: android - listview get item view by position
    Link: https://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position
     */
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
