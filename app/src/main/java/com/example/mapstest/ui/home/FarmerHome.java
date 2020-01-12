package com.example.mapstest.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mapstest.R;

public class FarmerHome extends Fragment {

    private HomeViewModel homeViewModel;
    private boolean draweropened;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView imageView =(ImageView) root.findViewById(R.id.drawerbutton);
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
//
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                draweropened=true;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                draweropened=false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(draweropened)
                drawer.closeDrawer(Gravity.RIGHT);
                else
                drawer.openDrawer(Gravity.LEFT);

            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
//
//package com.example.mapstest.ui.home;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.mapstest.R;
//
//public class FarmerHome extends AppCompatActivity {
//
//    LinearLayout personalinfo, experience, review;
//    TextView personalinfobtn, experiencebtn, reviewbtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home);
//
//        personalinfo = findViewById(R.id.personalinfo);
//        experience = findViewById(R.id.experience);
//        review = findViewById(R.id.review);
//        personalinfobtn = findViewById(R.id.personalinfobtn);
//        experiencebtn = findViewById(R.id.experiencebtn);
//        reviewbtn = findViewById(R.id.reviewbtn);
//        /*making personal info visible*/
//        personalinfo.setVisibility(View.VISIBLE);
//        experience.setVisibility(View.GONE);
//        review.setVisibility(View.GONE);
//
//
//        personalinfobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                personalinfo.setVisibility(View.VISIBLE);
//                experience.setVisibility(View.GONE);
//                review.setVisibility(View.GONE);
//                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));
//                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
//                reviewbtn.setTextColor(getResources().getColor(R.color.grey));
//
//            }
//        });
//
//        experiencebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                personalinfo.setVisibility(View.GONE);
//                experience.setVisibility(View.VISIBLE);
//                review.setVisibility(View.GONE);
//                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
//                experiencebtn.setTextColor(getResources().getColor(R.color.blue));
//                reviewbtn.setTextColor(getResources().getColor(R.color.grey));
//
//            }
//        });
//
//        reviewbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                personalinfo.setVisibility(View.GONE);
//                experience.setVisibility(View.GONE);
//                review.setVisibility(View.VISIBLE);
//                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
//                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
//                reviewbtn.setTextColor(getResources().getColor(R.color.blue));
//
//            }
//        });
//    }
//}
