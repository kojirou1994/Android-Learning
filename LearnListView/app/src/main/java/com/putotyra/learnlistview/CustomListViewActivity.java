package com.putotyra.learnlistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.PersistableBundle;

public class CustomListViewActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new CustomListViewAdapter(this));
    }

}