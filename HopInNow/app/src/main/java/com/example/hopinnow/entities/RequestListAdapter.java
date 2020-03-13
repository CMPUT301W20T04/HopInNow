package com.example.hopinnow.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.hopinnow.R;
import com.example.hopinnow.activities.RequestListFragment;

import java.util.ArrayList;

public class RequestListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Request> list = new ArrayList<Request>();
    private Context context;



    public RequestListAdapter(ArrayList<Request> list, Context context) {
        try{
            this.list = list;
            this.context = context;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_single_request, null);
        }

        //Handle TextView and display string from your list
        TextView fromLoc = (TextView)view.findViewById(R.id.single_request_from);
        TextView toLoc = (TextView)view.findViewById(R.id.single_request_to);
        fromLoc.setText(list.get(position).getPickUpLocName());
        toLoc.setText(list.get(position).getDropOffLocName());

        //Handle buttons and add onClickListeners
        Button acceptBtn = (Button)view.findViewById(R.id.accept_btn);

        acceptBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
