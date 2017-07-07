package com.tatlicilar.sosyalmedyauygulamasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by neval on 14/06/2017.
 */

public class MyAdapter  extends BaseAdapter {
        private ArrayList<Kisi> data;
        private Context context;

        //ArrayList Kisi objelerinden oluşuyor

        MyAdapter(Context context, ArrayList<Kisi> data) {
            this.context = context;
            this.data = data;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View rowView = convertView;

            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.list_item, null);
            }

            ImageView image = (ImageView) rowView.findViewById(R.id.listImg);
            TextView isim = (TextView) rowView.findViewById(R.id.listIsim);


            // Get individual object from  ArrayList<Kisi> and set ListView items
            Kisi temp_data = data.get(position);
            image.setImageResource(temp_data.getResim());
            isim.setText(temp_data.getIsim());

            return rowView;
        }
    }


