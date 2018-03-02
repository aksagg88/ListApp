package com.mokxa.learn.listapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by enterprise on 3/1/18.
 */

class ListItemsAdapter extends RecyclerView.Adapter<ListItemsHolder>{

    private ArrayList<ListItem> mListItems;
    private static final String TAG = "ListItemAdapter";

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListItemsAdapter(ArrayList<ListItem> mListItems) {
        this.mListItems = mListItems;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view  = using layout inflater
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent,false);

        // return viewholder
        return new ListItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemsHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ListItem s = mListItems.get(position);
        holder.BindData(s);

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }
}
