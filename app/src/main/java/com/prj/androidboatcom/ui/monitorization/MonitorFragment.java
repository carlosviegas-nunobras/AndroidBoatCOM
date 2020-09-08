package com.prj.androidboatcom.ui.monitorization;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.prj.androidboatcom.Listener;
import com.prj.androidboatcom.R;

import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;


public class MonitorFragment extends RosActivity {

    Listener listener;
    URI masterURI;
    public MonitorFragment() {
        super("PRJ", "PRJ", URI.create(""));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_monitor);
        masterURI=  URI.create(getIntent().getStringExtra("masterURI"));
        listener= new Listener(this);
    }

    @Override
    public void init(NodeMainExecutor nodeMainExecutor) {

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(), masterURI);
        nodeConfiguration.setNodeName("testing");
        nodeMainExecutor.execute(listener,nodeConfiguration);

    }
}