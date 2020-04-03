package com.example.hopinnow.entities;

import android.annotation.SuppressLint;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

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
    private ArrayList<Request> list;
    private Context context;

    /**
     * constructor
     * @param list
     *      the request list
     * @param context
     *      context of current application
     */
    public RequestListAdapter(ArrayList<Request> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * get the size of request list
     * @return
     *      get the request list
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * get the request item at specific position
     * @param pos
     *      position
     * @return
     *      an object specifying the position
     */
    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    /**
     * get item id (not used)
     * @param position
     *      the position
     * @return
     *      a long type value
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * get the view of one request row and add it to the listView
     * @param position
     *      the position object
     * @param convertView
     *      a convert view
     * @param parent
     *      the parent of current context
     * @return
     *      a view object
     */
    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = Objects.requireNonNull(inflater)
                    .inflate(R.layout.layout_single_request, null);
        }

        //Handle TextView and display string from your list
        TextView fromLoc = view.findViewById(R.id.single_request_from);
        TextView toLoc = view.findViewById(R.id.single_request_to);
        TextView fare = view.findViewById(R.id.single_request_fare);
        fromLoc.setText(list.get(position).getPickUpLocName());
        toLoc.setText(list.get(position).getDropOffLocName());
        fare.setText(list.get(position).getEstimatedFare().toString());

        //Handle buttons and add onClickListeners
        Button acceptBtn = view.findViewById(R.id.accept_btn);

        acceptBtn.setOnClickListener(v -> {
            //do something
            notifyDataSetChanged();
        });
        return view;
    }
}
