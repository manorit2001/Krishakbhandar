package com.example.mapstest;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.reactivex.rxjava3.subjects.PublishSubject;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<WarehouseModel> dataSet;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView listimagewarehouseIcon;
        TextView listloc;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.listnamewarehouse);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.listdistwarehouse);
            this.listimagewarehouseIcon = (ImageView) itemView.findViewById(R.id.listimagewarehouse);
            this.listloc= (TextView) itemView.findViewById(R.id.listlocwarehouse);
        }
    }

    public CustomAdapter(ArrayList<WarehouseModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

//        view.setOnClickListener(lists.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView listimagewarehouse = holder.listimagewarehouseIcon;
        TextView listloc = holder.listloc;

        textViewName.setText(dataSet.get(listPosition).get_name());
        textViewVersion.setText(dataSet.get(listPosition).get_dist());
        listimagewarehouse.setImageResource(dataSet.get(listPosition).get_image());
        listloc.setText(dataSet.get(listPosition).get_loc());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClickSubject.onNext(element);
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("NAME", dataSet.get(listPosition).get_name());
                intent.putExtra("DIST", dataSet.get(listPosition).get_dist());
                intent.putExtra("LOC", dataSet.get(listPosition).get_loc());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
