package com.example.wenyang.sharefoliomessenger.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wenyang.sharefoliomessenger.R;
import com.example.wenyang.sharefoliomessenger.model.Friend;

import java.util.ArrayList;

/**
 * Created by WenYang on 12/05/2016.
 */
public class ListFriendAdapter extends ArrayAdapter<Friend> {

        private final Activity context;
        private final ArrayList<Friend> list;

        public ListFriendAdapter(Activity context, ArrayList<Friend> list) {
            super(context, R.layout.list_friends, list);
            this.context = context;
            this.list = list;
        }

        static class ViewHolder {
            protected TextView text;
            protected TextView device_id;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                convertView = inflator.inflate(R.layout.list_friends, null);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView) convertView
                        .findViewById(R.id.person_name);
                viewHolder.device_id = (TextView) convertView
                        .findViewById(R.id.device_id);
                viewHolder.device_id.setVisibility(View.GONE);
                viewHolder.text.setTextColor(Color.BLACK);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (list != null) {
                Friend h = list.get(position);
                viewHolder.text.setText(h.getPersonName());
                viewHolder.device_id.setText(h.getRegId());
            }

            return convertView;
        }
}

