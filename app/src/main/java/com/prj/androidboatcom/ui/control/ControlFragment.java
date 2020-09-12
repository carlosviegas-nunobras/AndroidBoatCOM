package com.prj.androidboatcom.ui.control;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.prj.androidboatcom.Global;
import com.prj.androidboatcom.R;
import com.prj.androidboatcom.Talker;
import com.prj.androidboatcom.ui.log.Type;

import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;


public class ControlFragment extends RosActivity {
    Talker talker;
    private   DateFormat DFormat;


    private URI masterURI;
    public ControlFragment() {
        super("PRJ", "PRJ", URI.create(""));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_control);
        masterURI=  URI.create(getIntent().getStringExtra("masterURI"));
    }

    @Override
    public void init(NodeMainExecutor nodeMainExecutor) {
        talker = new Talker(this,masterURI);

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(),masterURI);
       nodeConfiguration.setNodeName("testing");
        nodeMainExecutor.execute(talker,nodeConfiguration);

       DFormat = DateFormat.getTimeInstance();
        String message = "Controlo Ligado - " + DFormat.format(new Date());
       Global.saveLog(message, Type.CONTROL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}