package com.prj.androidboatcom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.prj.androidboatcom.ui.monitorization.MonitorFragment;


public class Start extends AppCompatActivity {

    private String masterURI ="";
    private final String MONITOR_ID = "m";
    private final String TRAJLOC_ID = "l";
    private final String CONTROL_ID = "c";

Button controlButton;
Boolean control = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        controlButton = findViewById(R.id.control);
        Intent intent = getIntent();

       masterURI=  intent.getStringExtra("masterURI");





    }

    public void onMenuItemSelected(View view){
        Log.d("xa","xa3xa");
        Intent i;
        switch (view.getId()){

            case R.id.monotor:

                i = new Intent(getApplicationContext(), MonitorFragment.class);
                i.putExtra("frag",MONITOR_ID);
                Log.d("xa","xaxa " + masterURI);
                i.putExtra("masterURI",masterURI);
                startActivity(i);
                break;
            case R.id.trajloc:

                i = new Intent(getApplicationContext(), Main.class);
                i.putExtra("frag",TRAJLOC_ID);
                i.putExtra("PAK",3);
                Log.d("xa","xaxa");

                startActivity(i);
                break;
            case R.id.control:

                i = new Intent(getApplicationContext(), Main.class);
                i.putExtra("frag",CONTROL_ID);
                i.putExtra("masterURI",masterURI);

                Log.d("xa","xaxa");

                startActivity(i);
                break;
            default:
                Log.i("ye","banana");

        }
    }

    public void toMainActivity(View view){

        Intent i = new Intent(getApplicationContext(), MonitorFragment.class);
        startActivity(i);
    }
}
