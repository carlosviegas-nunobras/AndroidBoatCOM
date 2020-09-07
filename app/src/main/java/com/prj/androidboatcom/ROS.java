package com.prj.androidboatcom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import org.ros.rosjava_tutorial_pubsub.Talker;

import java.net.URI;


public class ROS extends RosActivity {
    private Listener listener;
    private boolean sentAppToBackground=false;
    private String frag="";

    public ROS(){
        super("XA","XA",URI.create("http://192.168.1.87:11311"));

        Log.d("XAPA","XAXAXAXA");
    }

    public ROS(String masterURI){
        super("XA","XA",URI.create(masterURI));

    }
     public ROS(Listener listener, String notificationTicker, String notificationTitle,String masterURI) {
        super(notificationTicker, notificationTitle, URI.create(masterURI));
        Log.d("Xapa","COMIKEE");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new Listener();
        sentAppToBackground = moveTaskToBack(true);
        Intent it = getIntent();

            Intent i = new Intent(getApplicationContext(),Main.class);
                i.putExtra("frag",it.getStringExtra("frag"));
            this.startActivity(i);
        }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        // At this point, the user has already been prompted to either enter the URI
        // of a master to use or to start a master locally.

        // The user can easily use the selected ROS Hostname in the master chooser
        // activity.
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        Log.d("XAPA",getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());

        //nodeMainExecutor.execute(listener, nodeConfiguration);

        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        nodeMainExecutor.execute(listener, nodeConfiguration);
    }
}
