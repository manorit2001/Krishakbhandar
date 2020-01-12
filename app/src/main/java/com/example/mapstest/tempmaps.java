package com.example.mapstest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tempmaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderCLient;
    private PlacesClient placesclient;
    private List<AutocompletePrediction> predictionList;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private LatLng locselected;

    private MaterialSearchBar materialSearchBar;
    private Button bttnfind;
    private View mapView;
    private final float DEFAULT_ZOOM = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        materialSearchBar = findViewById(R.id.searchBar);
        bttnfind = findViewById(R.id.bttnfind);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        mFusedLocationProviderCLient = LocationServices.getFusedLocationProviderClient(tempmaps.this);
        if(!Places.isInitialized())
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        placesclient = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        bttnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locselected = mMap.getCameraPosition().target;
                Intent intent=new Intent();
                intent.putExtra("latitude",locselected.latitude);
                intent.putExtra("longitude",locselected.longitude);
                Log.i("Result", locselected.latitude + "," + locselected.longitude);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, false);
//                if(!materialSearchBar.isSuggestionsVisible())
//                    materialSearchBar.showSuggestionsList();
//                else
//                    materialSearchBar.hideSuggestionsList();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {

                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }

            }
        });


        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setQuery(s.toString()).setSessionToken(token).setCountry("in").setTypeFilter(TypeFilter.ADDRESS).build();
//                if(predictionsRequest!=null)
                /*placesclient.findAutocompletePredictions(predictionsRequest)
                        .addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                               @Override
                               public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                                   if (task.isSuccessful()) {
                                       FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                                       if (predictionsResponse != null) {
                                           predictionList = predictionsResponse.getAutocompletePredictions();
                                           List<String> suggestionList = new ArrayList<>();
                                           for (AutocompletePrediction prediction : predictionsResponse.getAutocompletePredictions()) {
//                                           for (int i = 0; i < predictionList.size(); i++) {
//                                               AutocompletePrediction prediction = predictionList.get(i);
                                               suggestionList.add(prediction.getFullText(null).toString());
                                               Log.i("list", prediction.getFullText(null).toString());
                                           }
                                           materialSearchBar.updateLastSuggestions(suggestionList);
                                           if (!materialSearchBar.isSuggestionsVisible()) {
                                               materialSearchBar.showSuggestionsList();
                                           }
                                       }
                                   } else {
                                       Log.i("mytag", "unable to perform predictions");
                                   }
                            }
                       }
                );
*/
                String TAG;
                TAG = "output";
                placesclient.findAutocompletePredictions(predictionsRequest).addOnSuccessListener((response) -> {
                    List<String> suggestionList = new ArrayList<>();
                    predictionList = response.getAutocompletePredictions();
                    for (AutocompletePrediction prediction : predictionList) {
                        suggestionList.add(prediction.getFullText(null).toString());
                        Log.i(TAG, prediction.getPlaceId());
                        Log.i(TAG, prediction.getPrimaryText(null).toString());
                    }
                    materialSearchBar.updateLastSuggestions(suggestionList);
                    if(!materialSearchBar.isSuggestionsVisible()){
                        materialSearchBar.showSuggestionsList();
                    }
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                        Toast.makeText(tempmaps.this,"Place not found: " + apiException.getStatusCode(),Toast.LENGTH_SHORT).show();
                    }
                });

                materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
                    @Override
                    public void OnItemClickListener(int position, View v) {
                        if(position>predictionList.size())
                            return;
                        AutocompletePrediction selectedPrediction = predictionList.get(position);
                        String suggestion= materialSearchBar.getLastSuggestions().get(position).toString();
                        materialSearchBar.setText(suggestion);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            materialSearchBar.clearSuggestions();

                            }
                        },1000);
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if(imm!=null)
                            imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
                        String placeId= selectedPrediction.getPlaceId();
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
                        FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId,placeFields).setSessionToken(token).build();
                        placesclient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @Override
                            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                LatLng latLng = fetchPlaceResponse.getPlace().getLatLng();
                                if(latLng != null) {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                                    locselected = latLng;
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if ( e instanceof ApiException)
                                {
                                    ApiException apiException = (ApiException) e;
                                    apiException.printStackTrace();
                                    Log.e("fetchplace","Place not found" + apiException.getStatusCode());
                                    Toast.makeText(tempmaps.this,"Place not found" + apiException.getStatusCode(),Toast.LENGTH_SHORT);
                                }
                            }
                        });
                    }

                    @Override
                    public void OnItemDeleteListener(int position, View v) {


                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (mapView != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if(materialSearchBar.isSuggestionsVisible())
                        materialSearchBar.clearSuggestions();
                    if(materialSearchBar.isSearchEnabled())
                        materialSearchBar.disableSearch();
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        gpspopup();
                    } else {
                        gpsEnabled();
                    }
                }
            });
        }

        //check if GPS enables
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(5000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//
//        SettingsClient settingsClient = LocationServices.getSettingsClient(tempmaps.this);
//        gpspopup();


    }


    private void gpspopup() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(tempmaps.this);

        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(tempmaps.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                gpsEnabled();
            }
        });

        task.addOnFailureListener(tempmaps.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(tempmaps.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK)
                gpsEnabled();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        gpsEnabled();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void gpsEnabled() {
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        mFusedLocationProviderCLient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                //                            final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                                LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null)
                                            return;
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        LatLng latLng=new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                                        locselected=latLng;
                                        mFusedLocationProviderCLient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                //                            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationCallback);
                                mFusedLocationProviderCLient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        } else {
                            Toast.makeText(tempmaps.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
