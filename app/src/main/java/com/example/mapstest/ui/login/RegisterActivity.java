package com.example.mapstest.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mapstest.R;
import com.example.mapstest.tempmaps;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<View> views = new ArrayList<View>();
    final private int LOCATION_RES=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Spinner spinner = findViewById(R.id.spinner);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ArrayList<View> views = new ArrayList<View>();
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear1);
                for(int i=0;i<views.size();i++)
                    linearLayout.removeView(views.get(i));
                views.clear();

                switch (parent.getItemAtPosition(position).toString())
                {
                    case "Farmer":
                    {
                        views = farmer(views);
                    }
                    case "Retailer":
                    {

                    }
                    case "Warehouse":
                    {

                    }
                }
                for(int i=0;i<views.size();i++)
                    linearLayout.addView(views.get(i));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        <EditText
                    android:id="@+id/firstname"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="@dimen/sign_up_gap"
                    android:layout_marginRight="@dimen/sign_up_gap"
                    android:layout_marginLeft="@dimen/sign_up_gap"
                    android:layout_marginBottom="@dimen/sign_up_gap"
                    android:layout_below="@id/spinner"
                    android:hint="First Name"
                    android:inputType="textPersonName"
                    android:selectAllOnFocus="true"
                    android:textStyle="italic" />
         */
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
//        categories.add("Customer");
        categories.add("Farmer");
        categories.add("Retailer");
        categories.add("Warehouse OR Cold Storage");
//        categories.add("Delivery");
//        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    public ArrayList<View> farmer(ArrayList<View> views)
    {
        //edit text
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById(R.id.dummyedit).getLayoutParams();
//                        layoutParams.topMargin = R.dimen.sign_up_gap;
//                        layoutParams.bottomMargin = R.dimen.sign_up_gap;
        EditText editText=new EditText(RegisterActivity.this);
        editText.setHint("dummy");
        views.add(editText);
        editText.setLayoutParams(layoutParams);

        //text view
        layoutParams = (LinearLayout.LayoutParams) findViewById(R.id.dummytext).getLayoutParams();
        TextView textView= new TextView(RegisterActivity.this);
        textView.setText("Location");
        views.add(textView);
        textView.setLayoutParams(layoutParams);

        //button
        Button locbtn = new Button(RegisterActivity.this);
        layoutParams = (LinearLayout.LayoutParams) findViewById(R.id.dummybutton).getLayoutParams();
//                        layoutParams.topMargin = R.dimen.sign_up_gap;
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sign_up_gap);
//                        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.sign_up_gap),0,getResources().getDimensionPixelSize(R.dimen.sign_up_gap),getResources().getDimensionPixelSize(R.dimen.sign_up_gap));
        locbtn.setText("Set");
        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RegisterActivity.this, tempmaps.class),LOCATION_RES);
            }
        });
        views.add(locbtn);
        locbtn.setLayoutParams(layoutParams);

        return views;

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_RES && resultCode == Activity.RESULT_OK) {
            //TODO:get the returned LatLng object
//            LatLng latlng= data.getExtras();
//            Log.e("Result", result);
        }
    }
}
