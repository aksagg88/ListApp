package com.mokxa.learn.listapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by enterprise on 3/1/18.
 */

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class ListItemsHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ListItemsHolder";
    // define all the data and instantiate the views
    public TextView mNameTextView;

    public ListItemsHolder(View itemView) {
        super(itemView);
        //set click listener for the ViewHolder's View
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Element" + getAdapterPosition() + "clicked");
            }
        });

        //attach views to the ui resources
        mNameTextView = (TextView) itemView.findViewById(R.id.textView_name);

    }

    public void BindData (ListItem listItem){
        //method to bind data to the ui resource.
        mNameTextView.setText(listItem.getListItemText());
    }
}
