package com.prj.androidboatcom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.MapView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prj.androidboatcom.ui.localization.LocalFragment;

import org.ros.android.AppCompatRosActivity;


public class Main extends AppCompatActivity {

    AppCompatRosActivity rosActivity;

            MapView mapView;
    Fragment frag;
    Boolean control = true;
    private String masterURI = "";
    Listener listener;
    BottomNavigationView bottomNav;






    @Override
    protected void onResume() {
        super.onResume();
        Log.d("XAPA","IM BAAAACK");
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent i = getIntent();
        masterURI = i.getStringExtra("masterURI");
        Log.d("XAVA", "URI: " + masterURI);
      //  listener = new Listener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);



        System.out.println("BAKA");
        int selected_id = 0;
        Log.i("pata", "" + i.getStringExtra("frag") + "  " + i.getIntExtra("PAK", 0));
        String fragv = i.getStringExtra("frag");

        if (fragv.equals("l")) {
            frag = new LocalFragment();

        } else if (fragv.equals("m")) {
          //  frag = new MonitorFragment();
            selected_id = R.id.navigation_monit;

        } else if (fragv.equals("l")) {
            frag = new LocalFragment();
            selected_id = R.id.navigation_local;


        } else if (fragv.equals("c")) {
          //  frag = new ControlFragment();
            selected_id = R.id.navigation_control;


        }
        mapView= findViewById(R.id.mapView);
        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        bottomNav = findViewById(R.id.nav_view);

        // bottomNav.getMenu().clear();
        //  bottomNav.inflateMenu(R.menu.bottom_nav_menu_view);

//                    DisplayMetrics metrics = new DisplayMetrics();
//                    getWindowManager().getDefaultDisplay().getMetrics(metrics);


//                    bottomNav.setMinimumWidth(metrics.widthPixels);

        Log.d("XPOPA", "VALOR DO CONTROL: " + control);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(selected_id);

        if (savedInstanceState == null) {
            ((AppCompatActivity) getApplicationContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    frag).commit();
        }

        Intent startRos = new Intent(getApplicationContext(),ROS.class);
        startRos.putExtra("frag",fragv);
        startActivity(startRos);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.navigation_monit:
                         //   selectedFragment = new MonitorFragment();
                            break;

                        case R.id.navigation_local:

                            selectedFragment = new LocalFragment();


                            break;

                        case R.id.navigation_control:
                            //selectedFragment = new ControlFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };




}