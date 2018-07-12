package com.putotyra.learnlistview;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private ArrayAdapter<ListCellData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<ListCellData>(this, android.R.layout.simple_list_item_1);
//        adapter = new ArrayAdapter<String>(this, R.layout.ist_cell);

        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        adapter.add(new ListCellData("Ming", "Man", 17));
        adapter.add(new ListCellData("Li", "Woman", 15));

//        adapter.add("Hello");
//        adapter.add("Cosmos");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListCellData data = adapter.getItem(position);

        Toast.makeText(this, String.format("Name: %s, Sex: %s, Age: %d", data.getUserName(), data.getSex(), data.getAge()), Toast.LENGTH_SHORT).show();
    }
}
