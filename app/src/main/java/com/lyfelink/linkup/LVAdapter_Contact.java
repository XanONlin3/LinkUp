package com.lyfelink.linkup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Package: com.lyfelink.linkup, Project: LinkUp.
 * Created by Jan on 09.14.2014.
 */
public class LVAdapter_Contact extends BaseAdapter {

    //Base class from with all list data extends
    public static abstract class Row{}

    //Header Row
    public static final class Header extends Row{
        public final String TEXT;

        public Header(String text){
            this.TEXT =text;
        }
    }

    //Item Row
    public static final class Item extends Row{
        public final String TEXT;
        //public final String SUBTEXT;

        public Item(String text){   //, String subtext){
            this.TEXT = text;
            //this.SUBTEXT =subtext;
        }
    }

    private List<Row> rows;

    public void setRows(List<Row> rows){
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getItemViewType(int position){
        if(getItem(position) instanceof Header){
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if(getItemViewType(position) == 0) { //Item
            if(row == null){
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                row = inflater.inflate(R.layout.lv_item_contacts, parent, false);
            }

            Item item = (Item) getItem(position);
            TextView textView = (TextView) row.findViewById(R.id.txtViewName);
            textView.setText(item.TEXT);
        }
        else{ //Header
            if(row == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                row = inflater.inflate(R.layout.lv_header_main, parent, false);
            }

            Header header = (Header) getItem(position);
            TextView textView = (TextView) row.findViewById(R.id.txtViewHeader);
            textView.setText(header.TEXT);
        }

        return row;
    }
}
