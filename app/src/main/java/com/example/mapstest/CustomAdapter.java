package com.example.mapstest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<WarehouseModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView listimagewarehouseIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.listnamewarehouse);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.listdistwarehouse);
            this.listimagewarehouseIcon = (ImageView) itemView.findViewById(R.id.listimagewarehouse);
        }
    }

    public CustomAdapter(ArrayList<WarehouseModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_lists, parent, false);

//        view.setOnClickListener(lists.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView listimagewarehouse = holder.listimagewarehouseIcon;

        textViewName.setText(dataSet.get(listPosition).get_name());
        textViewVersion.setText(dataSet.get(listPosition).get_dist());
        listimagewarehouse.setImageResource(dataSet.get(listPosition).get_image());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
