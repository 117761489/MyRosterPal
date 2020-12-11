package com.example.myrosterpal;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//Code adapted from IS4447 lecture by Michael Gleeson - 03/12/20

public class ListViewAdapter extends BaseAdapter {
    private Activity activity;
    private List<Staff> listStaff;

    public ListViewAdapter(Activity activity, List<Staff> listStaff){
        this.activity = activity;
        this.listStaff = listStaff;
    }

    @Override
    public int getCount() {
        return listStaff.size();
    }

    @Override
    public Object getItem(int position) {
        return listStaff.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.row_listview, null);

            holder = new ViewHolder();
            holder.textViewFirstName = convertView.findViewById(R.id.textViewFirstName);
            holder.textViewLastName = convertView.findViewById(R.id.textViewLastName);
            holder.textViewAge = convertView.findViewById(R.id.textViewAge);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textViewFirstName.setText(listStaff.get(position).getFirstName());
        holder.textViewLastName.setText(listStaff.get(position).getLastName());
        holder.textViewAge.setText(listStaff.get(position).getAge() + "");

        return convertView;
    }

    class ViewHolder{
        TextView textViewFirstName;
        TextView textViewLastName;
        TextView textViewAge;
    }
}
