package com.prj.androidboatcom.ui.log;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prj.androidboatcom.Global;
import com.prj.androidboatcom.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogFragment extends AppCompatActivity {

    private int numEvent = 0;
    private final static String TAG = "LOG_ACTIVITY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.log_activity);
        DateFormat DFormat = DateFormat.getTimeInstance();




        Button clear = findViewById(R.id.ClearButton);
        Button sep = findViewById(R.id.SepButton);
        final LinearLayout EventsLayout = findViewById(R.id.EventsLayout);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsLayout.removeAllViews();
            }
        });

        sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveLogOnSD();
            }
        });

    getLogs();


    }

    public void getLogs(){
        for(int i=0; i< Global.messages.size(); i++){
            log(Global.messages.get(i),Global.types.get(i));
        }
    }

    public void saveLogOnSD() {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String sFileName = "AndroidBoatCom_Log_" + formatter.format(date).replace(" ","_")+".txt";
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            for(int i=0;i<Global.messages.size();i++) {
                writer.append(Global.messages.get(i)+"\n");
            }
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(), "O seu Log foi guardado.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void log(String message, Enum type){
        Log.d(TAG, message);

        LinearLayout EventsLayout = findViewById(R.id.EventsLayout);
        TextView valueTV = new TextView(this);

        valueTV.setId(numEvent++);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.setMargins(5, 5, 5,5);
        valueTV.setLayoutParams(layout);
        valueTV.setTextSize(14);

        if(type == Type.ONCREATE) valueTV.setBackgroundColor(getResources().getColor(R.color.green));
        if(type == Type.BASE) valueTV.setBackgroundColor(getResources().getColor(R.color.yellowGreen));
        if(type == Type.SR) valueTV.setBackgroundColor(getResources().getColor(R.color.yellow));
        if(type == Type.SCREEN) valueTV.setBackgroundColor(getResources().getColor(R.color.orange));
        if(type == Type.SEP){
            valueTV.setBackgroundColor(getResources().getColor(R.color.grey));
            valueTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }


        valueTV.setText(message);
        EventsLayout.addView(valueTV);


    }
}
