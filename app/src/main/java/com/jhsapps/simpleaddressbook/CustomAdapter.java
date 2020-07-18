package com.jhsapps.simpleaddressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 장효석 on 2020-07-18.
 */
class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item> data;

    public CustomAdapter(Context context, ArrayList<Item> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);

        Item item = data.get(position);

        TextView name = convertView.findViewById(R.id.name);
        TextView number = convertView.findViewById(R.id.number);

        name.setText(item.name);
        number.setText(item.number);

        return convertView;
    }
}
