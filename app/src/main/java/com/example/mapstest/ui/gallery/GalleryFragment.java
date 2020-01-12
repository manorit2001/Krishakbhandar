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

import java.util.List;

//public class GalleryFragment extends Fragment {
//
//    private GalleryViewModel galleryViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
////        galleryViewModel =
////                ViewModelProviders.of(getActivity()).get(GalleryViewModel.class);
//        View root = inflater.inflate(R.layout.activity_list_warehouse, container, false);
////        final TextView textView = root.getActivity().findViewById(R.id.text_gallery);
////        galleryViewModel.getText().observe(getActivity(), new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
//        return root;
//    }
//}

        

public class GalleryFragment extends Fragment {
    //    private final int[] pics = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};
//    private final int[] descriptions = {R.string.text1, R.string.text2, R.string.text3, R.string.text4, R.string.text5};
//    private final String[] countries = {"PARIS", "SEOUL", "LONDON", "BEIJING", "THIRA"};
//    private final String[] places = {"The Louvre", "Gwanghwamun", "Tower Bridge", "Temple of Heaven", "Aegeana Sea"};
//    private final String[] temperatures = {"21°C", "19°C", "17°C", "23°C", "20°C"};
//    private final String[] times = {"Aug 1 - Dec 15    7:00-18:00", "Sep 5 - Nov 10    8:00-16:00", "Mar 8 - May 21    7:00-18:00"};
//    private List<WarehouseModel> data;

    private final int[] pics = MyWarehouseData.drawableArray;
    private final String[] wname = MyWarehouseData.nameArray;
    private final String[] wdistance = MyWarehouseData.distArray;
    private final String[] w_contact = MyWarehouseData.loc;

    private final String[] desciptions = wname;
    private final String[] places = wname;
    private final String[] times = wname;

    private final SliderAdapter sliderAdapter = new SliderAdapter(pics, 20, new OnCardClickListener());

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

    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_warehouse);
//
////        initData();
//        initRecyclerView();
//        initCountryText();
//        initSwitchers();
//    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                ViewModelProviders.of(getActivity()).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.activity_list_warehouse, container, false);
//        final TextView textView = root.getActivity().findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        initData();
        initRecyclerView(root);
        initCountryText(root);
        initSwitchers(root);
        return root;
    }

//    private  void initData(){
//        data = new ArrayList<WarehouseModel>();
//        int n = 11;
//        for(int i = 0; i < n; i++){
//            data.add(new WarehouseModel(
//                    MyWarehouseData.nameArray[i],
//                    MyWarehouseData.distArray[i],
//                    MyWarehouseData.id_[i],
//                    R.drawable.p2,
//                    MyWarehouseData.loc[i]
//            ));
//        }
//    }

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

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (isFinishing() && decodeMapBitmapTask != null) {
//            decodeMapBitmapTask.cancel(true);
//        }
//    }

    private void initSwitchers(View view) {
        temperatureSwitcher = view.findViewById(R.id.ts_temperature);
        temperatureSwitcher.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        temperatureSwitcher.setCurrentText(wdistance[0]);

        placeSwitcher = view.findViewById(R.id.ts_place);
        placeSwitcher.setFactory(new TextViewFactory(R.style.PlaceTextView, false));
        placeSwitcher.setCurrentText(places[0]);

        clockSwitcher = view.findViewById(R.id.ts_clock);
        clockSwitcher.setFactory(new TextViewFactory(R.style.ClockTextView, false));
        clockSwitcher.setCurrentText(times[0]);

        descriptionsSwitcher = view.findViewById(R.id.ts_description);
        descriptionsSwitcher.setInAnimation(view.getContext(), android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(view.getContext(), android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(wname[0]);

//        mapSwitcher = (ImageSwitcher) getActivity().findViewById(R.id.ts_map);
//        mapSwitcher.setInAnimation(getActivity(), R.anim.fade_in);
//        mapSwitcher.setOutAnimation(getActivity(), R.anim.fade_out);
//        mapSwitcher.setFactory(new ImageViewFactory());
//        mapSwitcher.setImageResource(maps[0]);

//        mapLoadListener = new DecodeBitmapTask.Listener() {
//            @Override
//            public void onPostExecuted(Bitmap bitmap) {
//                ((ImageView)mapSwitcher.getNextView()).setImageBitmap(bitmap);
//                mapSwitcher.showNext();
//            }
//        };
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

//    private void initGreenDot() {
//        mapSwitcher.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mapSwitcher.getViewTreeObserver().removeOnGlobalLayoutListener(getActivity());
//
//                final int viewLeft = mapSwitcher.getLeft();
//                final int viewTop = mapSwitcher.getTop() + mapSwitcher.getHeight() / 3;
//
//                final int border = 100;
//                final int xRange = Math.max(1, mapSwitcher.getWidth() - border * 2);
//                final int yRange = Math.max(1, (mapSwitcher.getHeight() / 3) * 2 - border * 2);
//
//                final Random rnd = new Random();
//
//                for (int i = 0, cnt = dotCoords.length; i < cnt; i++) {
//                    dotCoords[i][0] = viewLeft + border + rnd.nextInt(xRange);
//                    dotCoords[i][1] = viewTop + border + rnd.nextInt(yRange);
//                }
//
//                greenDot = getActivity().findViewById(R.id.green_dot);
//                greenDot.setX(dotCoords[0][0]);
//                greenDot.setY(dotCoords[0][1]);
//            }
//        });
//    }

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
        placeSwitcher.setText(places[pos % places.length]);

        clockSwitcher.setInAnimation(getActivity(), animV[0]);
        clockSwitcher.setOutAnimation(getActivity(), animV[1]);
        clockSwitcher.setText(times[pos % times.length]);

//        descriptionsSwitcher.setText(data.get(pos % data.size()).get_name());
        descriptionsSwitcher.setText(wname[pos % wname.length]);


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
                intent.putExtra("DIST", wdistance[activeCardPosition % wname.length]);
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
