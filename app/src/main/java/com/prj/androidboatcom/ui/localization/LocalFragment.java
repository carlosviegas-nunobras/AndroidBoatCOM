package com.prj.androidboatcom.ui.localization;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.prj.androidboatcom.Listener;
import com.prj.androidboatcom.R;

import org.ros.android.AppCompatRosActivity;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;

public class LocalFragment extends RosActivity implements OnMapReadyCallback {

     ImageView i;
    View v;

    private Boolean mLocationPermissionsGranted = false;
   private final int  TAG_CODE_PERMISSION_LOCATION = 26;
    private MapView mapView;
    private GoogleMap map;
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private URI masterURI;
    public LocalFragment() {
        super("PRJ", "PRJ", URI.create(""));

    }

    public GoogleMap getMap() {
        return map;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_local);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        masterURI=  URI.create(getIntent().getStringExtra("masterURI"));
        mapView.getMapAsync(this);


//rotateCompass();

    }

//    public void rotateCompass(){
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//
//                    i.setRotation(i.getRotation()+5);
//
//                }
//            }
//        };
//        Thread t = new Thread(r);
//        t.start();
//
//
//    }

    //public void updateLocation(GoogleMap map, LatLng pos,PolylineOptions route){

    public void updateLocation(GoogleMap map, LatLng pos, PolylineOptions route){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,20));
        map.addMarker(new MarkerOptions().position(pos));
        route = route.add(pos);
        map.addPolyline(route);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
      //  LatLng ENIDH = new LatLng(38.69067895,-9.30015289);
         LatLng ENIDH = new LatLng(38.687789, -9.296805);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {

        ActivityCompat.requestPermissions(this, new String[] {
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
        PolylineOptions route = new PolylineOptions().add(new LatLng(38.688449,-9.297581))
                .add(new LatLng(38.689269, -9.296714))
                .add(new LatLng(38.689792, -9.294893))
                .add(new LatLng(38.689305, -9.294125))
                .add(new LatLng(38.688423,-9.294193))
                .add(new LatLng(38.688312, -9.295661))
                .add(new LatLng(38.687789, -9.296805));

        route.color(Color.rgb(255,0,0));

        Polyline polyline = map.addPolyline(route);


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
    public void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(),masterURI);
        nodeConfiguration.setNodeName("testing");
        nodeMainExecutor.execute(new Listener(this),nodeConfiguration);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}