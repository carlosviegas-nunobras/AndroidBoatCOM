package com.prj.androidboatcom.ui.localization;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.prj.androidboatcom.R;

public class LocalFragment extends Fragment implements OnMapReadyCallback {

     ImageView i;
    View v;

    private Boolean mLocationPermissionsGranted = false;
   int  TAG_CODE_PERMISSION_LOCATION = 26;
    MapView mapView;
    GoogleMap map;
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_local,container,false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);


//rotateCompass();

        return v;
    }

    public ImageView getIV(){
        return i;
    }
    public void rotateCompass(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true){

                    i.setRotation(i.getRotation()+5);

                }
            }
        };
        Thread t = new Thread(r);
        t.start();


    }


    public void updateLocation(GoogleMap map, LatLng pos,PolylineOptions route){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,20));
        map.addMarker(new MarkerOptions().position(pos));
        route = new PolylineOptions().add(pos);

        Polyline polyline = map.addPolyline(route);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng ENIDH = new LatLng(38.69067895,-9.30015289);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {

        ActivityCompat.requestPermissions(getActivity(), new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION },
                TAG_CODE_PERMISSION_LOCATION);  }     /*
       //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
       */

        // Updates the location and zoom of the MapView
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ENIDH, 10);
       // map.animateCamera(cameraUpdate);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ENIDH,20));
        googleMap.addMarker(new MarkerOptions().position(ENIDH));
       /* PolylineOptions route = new PolylineOptions().add(new LatLng(38.69067895,-9.30015289))
                .add(new LatLng(38.69067895,-9.50015289));

        route.color(Color.rgb(255,0,0));

        Polyline polyline = map.addPolyline(route);*/


    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}