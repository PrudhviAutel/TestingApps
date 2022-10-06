package com.android.autelsdk.album.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.autelsdk.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalVideoListAdapter extends BaseAdapter {
    private List<File> mediaInfos = new ArrayList<>();
    private Context mContext;


    public LocalVideoListAdapter(Context context) {
        mContext = context;
    }


    public void setRfData(List<File> data) {
        this.mediaInfos = data;
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return null == mediaInfos ? 0 : mediaInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mediaInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.support_simple_spinner_dropdown_item, null);
        }
        textView = (TextView) convertView.findViewById(R.id.text1);

        if (position < mediaInfos.size() && position >= 0) {
            textView.setText(mediaInfos.get(position).getAbsolutePath());
        }

        return convertView;
    }
}
