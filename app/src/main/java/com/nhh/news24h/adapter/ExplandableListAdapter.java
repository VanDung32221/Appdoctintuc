package com.nhh.news24h.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhh.news24h.R;
import com.nhh.news24h.activity.MainActivity;

import java.util.HashMap;
import java.util.List;

public class ExplandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> mListDataHeader;

    private HashMap<String, List<String>> mListDataChild;
    private ExpandableListView expandableListView;

    public ExplandableListAdapter(Context context, List<String> mListDataHeader, HashMap<String, List<String>> mListDataChild) {
        this.context = context;
        this.mListDataHeader = mListDataHeader;
        this.mListDataChild = mListDataChild;
//        this.expandableListView = expandableListView;
    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        return i;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mListDataChild.get(
                this.mListDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_header, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.sub_menu);
        ImageView headerIcon = convertView.findViewById(R.id.iconimage);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.toString());
        headerIcon.setImageResource(MainActivity.icon[groupPosition]);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_submenu, null);
        }
        TextView tvListChild = convertView.findViewById(R.id.submenu);
        tvListChild.setText(childText.toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
