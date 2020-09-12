package com.prj.androidboatcom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.prj.androidboatcom.ui.control.ControlFragment;
import com.prj.androidboatcom.ui.localization.LocalFragment;
import com.prj.androidboatcom.ui.log.LogFragment;
import com.prj.androidboatcom.ui.monitorization.MonitorFragment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ros.internal.node.client.Registrar;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class Start extends AppCompatActivity {

    private String masterURI ="";
    private final String MONITOR_ID = "m";
    private final String TRAJLOC_ID = "l";
    private final String CONTROL_ID = "c";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();

       masterURI=  intent.getStringExtra("masterURI");


    }

    public void onMenuItemSelected(View view){
        Intent i;
        switch (view.getId()){

            case R.id.monotor:

                i = new Intent(getApplicationContext(), MonitorFragment.class);
                i.putExtra("frag",MONITOR_ID);
                i.putExtra("masterURI",masterURI);
                startActivity(i);
                break;
            case R.id.trajloc:

                i = new Intent(getApplicationContext(), LocalFragment.class);
                i.putExtra("frag",TRAJLOC_ID);
                i.putExtra("masterURI",masterURI);


                startActivity(i);
                break;
            case R.id.control:

                i = new Intent(getApplicationContext(), ControlFragment.class);
                i.putExtra("frag",CONTROL_ID);
                i.putExtra("masterURI",masterURI);


                startActivity(i);
                break;
            case R.id.log:
                i = new Intent(getApplicationContext(), LogFragment.class);

                startActivity(i);
                break;
            case R.id.sobre:
                i = new Intent(getApplicationContext(), About.class);

                startActivity(i);
                break;
            default:
                break;

        }
    }


}
