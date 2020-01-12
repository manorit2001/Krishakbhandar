package com.example.mapstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.content.Intent;

import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    SliderLayout sliderShow;
    private static int cart_count = 0;
    HashMap<String, String> url_maps = new HashMap<>();

    private GridView mGridView;
    private ProgressBar mProgressBar;
    List<String> bsp_id_list = new ArrayList<String>();
    private Bsp_Grid mGridAdapter;
    private ArrayList<GridItem> mGridData;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    LinearLayout l2;
    SharedPreferences.Editor editor;

    private Drawer result = null;
    private AccountHeader headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = sp.edit();
        l2 = findViewById(R.id.ll_best_selling);
        mProgressBar = findViewById(R.id.progressBar);

        final IProfile profile;
        if (sp.getString("loginid", null) == null) {
            profile = new ProfileDrawerItem().withName("RKS").withEmail("profile@rks.com").withIcon(R.drawable.splash_icon).withTag("RKS");

        } else {
            profile = new ProfileDrawerItem().withName(sp.getString("name", null)).withEmail(sp.getString("mobile", null)).withIcon(R.drawable.splash_icon).withTag("CUSTOMER");

        }

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            if (drawerItem.getTag().toString().equals("LOGIN")) {
                                Intent i = new Intent(HomeActivity.this, Splash.class);
                                startActivity(i);
                                finish();

                            } else if (drawerItem.getTag().toString().equals("ORDER_HISTORY")) {
                                Intent i = new Intent(HomeActivity.this, Splash.class);
                                startActivity(i);

                            } else if (drawerItem.getTag().toString().equals("MY_CART")) {
                                Intent i = new Intent(HomeActivity.this, Splash.class);
                                startActivity(i);

                            } else if (drawerItem.getTag().toString().equals("LOG_OUT")) {
                                cart_count = 0;
                                invalidateOptionsMenu();
                                editor.clear().apply();
                                Intent i = new Intent(HomeActivity.this, Splash.class);
                                startActivity(i);
                                finish();

                            } else if (drawerItem.getTag().toString().equals("CATEGORIES")) {

                            } else if (drawerItem.getTag().toString().equals("SUB_CATEGORIES")) {
                                Intent intent = new Intent(HomeActivity.this, Splash.class);
                                intent.putExtra("sub_cat_id", String.valueOf(drawerItem.getIdentifier()));
                                intent.putExtra("cart_count", "" + cart_count);
                                intent.putExtra("sub_category", ((Nameable) drawerItem).getName().toString());
                                startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
//                .withShowDrawerUntilDraggedOpened(true)
                .build();

        if (sp.getString("loginid", null) != null) {
            PrimaryDrawerItem order_history = new PrimaryDrawerItem().withName("Order History").withIcon(R.drawable.ic_history_black).withTag("ORDER_HISTORY");
            PrimaryDrawerItem my_cart = new PrimaryDrawerItem().withName("My Cart").withIcon(R.drawable.ic_shopping_cart_black).withTag("MY_CART");
            result.addItem(order_history);
            result.addItem(my_cart);
            result.addStickyFooterItem(new PrimaryDrawerItem().withName("Log Out").withIcon(R.drawable.ic_log_out).withTag("LOG_OUT"));




        } else {
            result.addStickyFooterItem(new PrimaryDrawerItem().withName("Log In").withIcon(R.drawable.ic_person_black).withTag("LOGIN"));
        }



        result.addItem(new DividerDrawerItem());
        result.addItem(new SecondaryDrawerItem().withName("Shop By Category").withTag("CATEGORY_LABEL").withSelectable(false).withSetSelected(false).withTextColor(getResources().getColor(R.color.material)));
        result.addItem(new DividerDrawerItem());


    }
}
