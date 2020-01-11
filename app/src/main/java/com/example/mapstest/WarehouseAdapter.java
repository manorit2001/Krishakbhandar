package com.example.mapstest;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WarehouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<WarehouseModel> viewitemlists;
    private Context context;

    public WarehouseAdapter(Context context, List<WarehouseModel> viewitemlists) {
        this.context = context;
        this.viewitemlists = viewitemlists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_header, parent, false);
            return new HeaderViewHolder(itemView);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            // You have to set your header items values with the help of model class and you can modify as per your needs
        }
        else if (holder instanceof ItemViewHolder){

            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            // Following code give a row of header and decrease the one position from listview items
            final WarehouseModel data = viewitemlists.get(position-1);

            // You have to set your listview items values with the help of model class and you can modify as per your needs
            itemViewHolder.name.setText(data.get_name());
            itemViewHolder.distance.setText(data.get_dist());
            itemViewHolder.contact_no.setText(data.get_loc());
            itemViewHolder.profile_image.setImageResource(data.get_image());

            // Handle click events
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                    intent.putExtra("NAME", data.get_name());
                    intent.putExtra("DIST", data.get_dist());
                    intent.putExtra("LOC", data.get_loc());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    // getItemCount increasing the position to 1. This will be the row of header
    @Override
    public int getItemCount() {
        return viewitemlists.size()+1;
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView headerImage;

        public HeaderViewHolder(View view) {
            super(view);
            headerImage = (ImageView) view.findViewById(R.id.warehouseimage);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name,distance,contact_no;
        LinearLayout latest_feed_layout;
        ImageView profile_image;
        public ItemViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.listnamewarehouse);
            distance = (TextView) itemView.findViewById(R.id.listdistwarehouse);
            contact_no = (TextView) itemView.findViewById(R.id.listlocwarehouse);
            profile_image = (ImageView) itemView.findViewById(R.id.listimagewarehouse);
            latest_feed_layout = (LinearLayout) itemView.findViewById(R.id.listwarehouselayout);
        }
    }
}
