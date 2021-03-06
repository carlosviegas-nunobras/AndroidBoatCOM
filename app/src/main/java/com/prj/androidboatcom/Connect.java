package com.prj.androidboatcom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.prj.androidboatcom.ui.log.Type;

import java.util.Enumeration;
import java.util.logging.LogManager;


public class Connect extends AppCompatActivity {

     EditText masterURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_connect);
        masterURI = findViewById(R.id.masterURI);




    }


    public void toStart(View view){

        Intent i = new Intent(this, Start.class);
        i.putExtra("masterURI",masterURI.getText().toString());
        Global.saveLog("Master URI '" + masterURI.getText().toString() +"' was saved.", Type.BASE);
        startActivity(i);
    }
}
