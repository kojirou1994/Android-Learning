package com.putotyra.learnlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Kojirou on 16/5/6.
 */
public class CustomListViewAdapter extends BaseAdapter {

    private Context context = null;

    public CustomListViewAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
    //        private String[] data = new String[]{"String1", "String2", "String3", "String4", "String5", "String6", "String1", "String2", "String3", "String4", "String5", "String6"};
    private CustomListCellData[] data = new CustomListCellData[] {
            new CustomListCellData("img1", "description1", R.mipmap.ic_favorites),
            new CustomListCellData("img2", "description2", R.mipmap.ic_friends),
            new CustomListCellData("img3", "description3", R.mipmap.ic_launcher)
    };
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public CustomListCellData getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//            System.out.println(">>>>>>>>>>");
//            TextView tv;
//            if (convertView != null) {
//                tv = (TextView) convertView;
//            }
//            else {
//                tv = new TextView(CustomListView.this);
//            }
//            tv.setTextSize(50);
//            tv.setText(getItem(position));
//            return tv;
        LinearLayout ll = null;
        if (convertView != null) {
            ll = (LinearLayout) convertView;
        }
        else {
            ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_listcell, null);
        }
        CustomListCellData data = getItem(position);

        ImageView icon = (ImageView) ll.findViewById(R.id.userIcon);
        TextView name = (TextView) ll.findViewById(R.id.name);
        TextView description = (TextView) ll.findViewById(R.id.description);
        icon.setImageResource(data.iconId);
        name.setText(data.name);
        description.setText(data.description);
        return ll;
    }
}
