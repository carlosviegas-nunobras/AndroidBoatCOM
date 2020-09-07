package com.prj.androidboatcom.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prj.androidboatcom.Listener;
import com.prj.androidboatcom.R;

import org.ros.android.RosActivity;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;


public class MonitorFragment extends RosActivity {

    Listener listener;
    protected MonitorFragment() {
        super("PRJ", "PRJ", URI.create("http://192.168.1.87:11311"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_monitor);

        listener= new Listener(this);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(),getMasterUri());
        nodeConfiguration.setNodeName("testing");
        nodeMainExecutor.execute(listener,nodeConfiguration);

    }
}