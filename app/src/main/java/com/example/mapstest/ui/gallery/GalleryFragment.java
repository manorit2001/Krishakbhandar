package com.example.mapstest.ui.gallery;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapstest.MyWarehouseData;
import com.example.mapstest.ProfileActivity;
import com.example.mapstest.R;
import com.example.mapstest.cards.SliderAdapter;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.Random;


public class GalleryFragment extends Fragment {
    private int[] pics = null;
    private final String[] wname = MyWarehouseData.nameArray;
    private final String[] wdistance = MyWarehouseData.distArray;
    private final String[] w_contact = MyWarehouseData.loc;
    private final String[] w_crops = MyWarehouseData.crops;

    private final String[] desciptions = w_crops;
//    private final String[] places = wname;
    private final String[] times = w_contact;

    private SliderAdapter sliderAdapter = null;

    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;
    private ImageSwitcher mapSwitcher;
    private TextSwitcher temperatureSwitcher;
    private TextSwitcher placeSwitcher;
    private TextSwitcher clockSwitcher;
    private TextSwitcher descriptionsSwitcher;

    private TextView country1TextView;
    private TextView country2TextView;
    private int countryOffset1;
    private int countryOffset2;
    private long countryAnimDuration;
    private int currentPosition;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_list_warehouse, container, false);
        if(getActivity().getIntent().getStringExtra("type").toString().equals("Retailer"))
        {
            pics = MyWarehouseData.farmer_img_array;
        }else
        {
            pics = MyWarehouseData.warehouse_img_array;
        }
        sliderAdapter = new SliderAdapter(pics, 20, new OnCardClickListener());
        initRecyclerView(root);
        initCountryText(root);
        initSwitchers(root);


        return root;
    }

    public String randomPlace(){
        Random rand = new Random();
        String[] cities = {"Delhi", "Haryana", "Punjab", "Mumbai", "Bhopal"};
        return cities[rand.nextInt(cities.length)];
    }


    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.warehouse_recycleview);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }


    private void initSwitchers(View view) {
        temperatureSwitcher = view.findViewById(R.id.ts_temperature);
        temperatureSwitcher.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        temperatureSwitcher.setCurrentText(wdistance[0]);

        placeSwitcher = view.findViewById(R.id.ts_place);
        placeSwitcher.setFactory(new TextViewFactory(R.style.PlaceTextView, false));
        placeSwitcher.setCurrentText(randomPlace());

        clockSwitcher = view.findViewById(R.id.ts_clock);
        clockSwitcher.setFactory(new TextViewFactory(R.style.ClockTextView, false));
        clockSwitcher.setCurrentText(times[0]);

        descriptionsSwitcher = view.findViewById(R.id.ts_description);
        descriptionsSwitcher.setInAnimation(view.getContext(), android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(view.getContext(), android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(desciptions[0]);

    }

    private void initCountryText(View view) {
        countryAnimDuration = getResources().getInteger(R.integer.labels_animation_duration);
        countryOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset);
        countryOffset2 = getResources().getDimensionPixelSize(R.dimen.card_width);
        country1TextView = view.findViewById(R.id.tv_country_1);
        country2TextView = view.findViewById(R.id.tv_country_2);

        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country1TextView.setText(wname[0]);
        country2TextView.setAlpha(0f);

        country1TextView.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "open-sans-extrabold.ttf"));
        country2TextView.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "open-sans-extrabold.ttf"));
    }


    private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (country1TextView.getAlpha() > country2TextView.getAlpha()) {
            visibleText = country1TextView;
            invisibleText = country2TextView;
        } else {
            visibleText = country2TextView;
            invisibleText = country1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }

        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        int[] animH = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
        int[] animV = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

//        setCountryText((data.get(pos % data.size()).get_name()), true);
        setCountryText(wname[pos % wname.length], true);


        temperatureSwitcher.setInAnimation(getActivity(), animH[0]);
        temperatureSwitcher.setOutAnimation(getActivity(), animH[1]);
        temperatureSwitcher.setText(wdistance[pos % wdistance.length]);

        placeSwitcher.setInAnimation(getActivity(), animV[0]);
        placeSwitcher.setOutAnimation(getActivity(), animV[1]);
        placeSwitcher.setText(randomPlace());

        clockSwitcher.setInAnimation(getActivity(), animV[0]);
        clockSwitcher.setOutAnimation(getActivity(), animV[1]);
        clockSwitcher.setText(times[pos % times.length]);

//        descriptionsSwitcher.setText(data.get(pos % data.size()).get_name());
        descriptionsSwitcher.setText(desciptions[pos % desciptions.length]);


        currentPosition = pos;
    }

    private class TextViewFactory implements  ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
//            this.styleId = 0;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(getActivity());

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(getActivity(), styleId);
            } else {
                textView.setTextAppearance(styleId);
            }
            return textView;
        }

    }

    private class ImageViewFactory implements ViewSwitcher.ViewFactory {
        @Override
        public View makeView() {
            final ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            final ViewGroup.LayoutParams lp = new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);

            return imageView;
        }
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm =  (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {
                final Intent intent = new Intent(getActivity(), ProfileActivity.class);
//                intent.putExtra(DetailsActivity.BUNDLE_IMAGE_ID, pics[activeCardPosition % pics.length]);
                intent.putExtra("NAME", wname[activeCardPosition % wname.length]);
                intent.putExtra("DIST", wdistance[activeCardPosition % wdistance.length]);
                intent.putExtra("LOC", w_contact[activeCardPosition % w_contact.length]);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);
                } else {
                    final CardView cardView = (CardView) view;
                    final View sharedView = cardView.getChildAt(cardView.getChildCount() - 1);
                    final ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), sharedView, "shared");
                    startActivity(intent, options.toBundle());
                }
            } else if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }
        }
    }

}
