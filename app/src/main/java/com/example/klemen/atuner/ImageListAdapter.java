package com.example.klemen.atuner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImageListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final Integer[] imgid;

    public ImageListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.items_list_item, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.items_list_item, null,true);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);



        imageView.setImageResource(imgid[position]);

        return rowView;

    };
}