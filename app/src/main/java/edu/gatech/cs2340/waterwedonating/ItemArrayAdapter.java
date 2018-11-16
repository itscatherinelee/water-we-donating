package edu.gatech.cs2340.waterwedonating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class for donation locations
 */
public class ItemArrayAdapter extends ArrayAdapter{
    private List<String[]> dataList = new ArrayList<String[]>();

    static class ItemViewHolder {
        TextView key;
        TextView name;
    }

    /**
     * Constructor initializes parameters to the parent constructor
     * @param context accepts context
     * @param textViewResourceId accepts view resource id
     */
    public ItemArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /**
     * Stores information associated with each location
     * @param object string array of object
     * locations and their information
     */
    public void add(String[] object) {
        dataList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.dataList.size();
    }

    @Override
    public String[] getItem(int index) {
        return this.dataList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.key = (TextView) row.findViewById(R.id.key);
            viewHolder.name = (TextView) row.findViewById(R.id.name);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.key.setText(stat[0]);
        viewHolder.name.setText(stat[1]);
        return row;
    }
}
