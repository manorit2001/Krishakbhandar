package com.example.mapstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class lists extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<WarehouseModel> data;
    private static List<WarehouseModel> warehouseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<WarehouseModel>();
        warehouseData = new ArrayList<WarehouseModel>();


        int n=11;
        for (int i = 0; i < n; i++) {
            warehouseData.add(new WarehouseModel(
                    MyWarehouseData.nameArray[i],
                    MyWarehouseData.distArray[i],
                    MyWarehouseData.id_[i],
                    MyWarehouseData.drawableArray[i],
                    MyWarehouseData.loc[i]

            ));
        }
        adapter = new WarehouseAdapter(getBaseContext(), warehouseData);
        recyclerView.setAdapter(adapter);
    }

}
