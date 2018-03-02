package com.mokxa.learn.listapp;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by enterprise on 3/1/18.
 */

public class ListItem {

    public String listItemText;
    public String listItemCreationDate;

    public ListItem() {
        // Default constructor required for calls to DataSnapshot.getValue(ListItem.class)
    }

    public ListItem(String listItemText) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.listItemCreationDate = sdf.format(new Date());
        this.listItemText = listItemText;
    }

    public String toString() {
        return this.listItemText + "\n" +this.getListItemCreationDate();
    }

    public String getListItemText() {
        return listItemText;
    }

    public void setListItemText(String listItemText) {
        this.listItemText = listItemText;
    }

    public String getListItemCreationDate() {
        return listItemCreationDate;
    }

    public void setListItemCreationDate(String listItemCreationDate) {
        this.listItemCreationDate = listItemCreationDate;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ListItemText", listItemText);
        result.put("ListItemCreationDate", listItemCreationDate);
        return result;
    }
}
