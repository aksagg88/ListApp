package com.mokxa.learn.listapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "ListActivity";

    private RecyclerView mListItemsRecyclerView;
    FragmentManager fm = getSupportFragmentManager();

    private ListItemsAdapter mAdapter;
    private ArrayList<ListItem> myListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //showDialog();
                createNewListItem();

            }
        });

        myListItems = CreateMockData();

        mListItemsRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_List);
        mListItemsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mListItemsRecyclerView.setLayoutManager(layoutManager);
        updateUI();
    }

    private void createNewListItem() {
        // Create new List item at /Listitem

        LayoutInflater li = LayoutInflater.from(this);
        View getListItemView  = li.inflate(R.layout.dialog_get_list_item,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getListItemView);

        final EditText userInput = (EditText) getListItemView.findViewById(R.id.editTextDialogUserInput);


        // set Dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // get user input and set it to result
                        // edit text
                        String listItemText = userInput.getText().toString();
                        ListItem listItem = new ListItem(listItemText);
                        myListItems.add(listItem);
                        //pushItemToDatabase(listItem);
                    }
                }).create()
                .show();
    }

    private void updateUI(){
        mAdapter = new ListItemsAdapter(myListItems);
        mListItemsRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<ListItem> CreateMockData() {
        String[] listitemtexts = {"item1", "item2", "item3", "item4"};
        ArrayList<ListItem> mMockItems = new ArrayList<>();
        for(int i=0; i<listitemtexts.length; i++){
            ListItem item = new ListItem(listitemtexts[i]);
            mMockItems.add(item);
        }
        return mMockItems;
    }

    private void showDialog() {
        AddItemDialogFragment dialogFragment = new AddItemDialogFragment();
        dialogFragment.show(fm,"Dialog Fragment" );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
