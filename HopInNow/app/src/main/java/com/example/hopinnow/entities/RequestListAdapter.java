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

/**
 * Citation:
 * Author: William Kinaan
 * Date: April 5, 2013
 * Title: Android custom Row Item for ListView
 * Website: https://stackoverflow.com/questions/15832335/android-custom-row-item-for-listview
 */
/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * request list adapter which helps to parse custom request view into list view
 */
public class RequestListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Request> list = new ArrayList<Request>();
    private Context context;

    /**
     * constructor
     * @param list
     * @param context
     */
    public RequestListAdapter(ArrayList<Request> list, Context context) {
        try{
            this.list = list;
            this.context = context;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get the size of request list
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * get the request item at specific position
     * @param pos
     * @return
     */
    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    /**
     * get item id (not used)
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * get the view of one request row and add it to the listView
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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
