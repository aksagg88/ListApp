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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "ListActivity";

    private RecyclerView mListItemsRecyclerView;
    private FloatingActionButton mfab;

    FragmentManager fm = getSupportFragmentManager();

    private ListItemsAdapter mAdapter;
    private ArrayList<ListItem> myListItems = null;

    private FirebaseDatabase mFirebasDatabase;
    private DatabaseReference mListItemDBReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize firebaseapp
        FirebaseApp.initializeApp(this);

        //Instantiate Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instantiate the firebase database
        mFirebasDatabase = FirebaseDatabase.getInstance();
        mListItemDBReference = mFirebasDatabase.getReference().child("listItem");

        //create Mock data
        myListItems = CreateMockData();

        //Instantiate Recyclerview and layout manager
        mListItemsRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_List);
        mListItemsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mListItemsRecyclerView.setLayoutManager(layoutManager);

        //read database
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Added",dataSnapshot.getValue(ListItem.class).toString());
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Changed",dataSnapshot.getValue(ListItem.class).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG+"Removed",dataSnapshot.getValue(ListItem.class).toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Moved",dataSnapshot.getValue(ListItem.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG+"Added",databaseError.toString());
            }
        };
        mListItemDBReference.addChildEventListener(mChildEventListener);

        //Instantiate the FAB
        mfab = (FloatingActionButton) findViewById(R.id.fab);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //showDialog();
                createNewListItem();

            }
        });

        //Instantiate the adapter/ Update UI.
        updateUI();
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        ListItem listItem = dataSnapshot.getValue(ListItem.class);
        myListItems.add(listItem);
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
                        //myListItems.add(listItem);
                        pushItemToDatabase(listItem);
                    }
                }).create()
                .show();
    }

    private void pushItemToDatabase(ListItem listItem) {
        mListItemDBReference.push().setValue(listItem);
    }

    private void updateUI(){
        mAdapter = new ListItemsAdapter(myListItems);
        mListItemsRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<ListItem> CreateMockData() {
        String[] listitemtexts = {"item1"};
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
        switch(id) {
            case R.id.action_delete:
                deleteAllListItems();
            case R.id.action_settings:
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllListItems() {
        FirebaseDatabase.getInstance().getReference().child("ListItem").removeValue();
        myListItems.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Items Deleted Successfully", Toast.LENGTH_SHORT).show();
    }
}
