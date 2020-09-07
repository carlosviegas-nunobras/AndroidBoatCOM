package com.prj.androidboatcom;


import android.content.res.Resources;

import com.prj.androidboatcom.ui.home.MonitorFragment;
import com.prj.androidboatcom.ui.notifications.ControlFragment;

import org.apache.commons.logging.Log;
import org.ros.android.view.VirtualJoystickView;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import de.nitri.gauge.Gauge;
import std_msgs.String;

public class ListenerC extends AbstractNodeMain {
    private java.lang.String value;
    Resources res;
    private ControlFragment controlFragment;
    public ListenerC(ControlFragment controlFragment) {
        value="";
        this.controlFragment= controlFragment;

    }
    public ListenerC(){

    }


    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/listener");
    }

    public java.lang.String getValue(){
        return  value;
    }
    public void onStart(ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();
        Subscriber<String> subscriber = connectedNode.newSubscriber("test", "std_msgs/String");
        subscriber.addMessageListener(new MessageListener<String>() {
            public void onNewMessage(String message) {
                      VirtualJoystickView g = controlFragment.findViewById(R.id.joystickview);
                System.out.println("bish"+message.getData());
                log.info("I heard: \"" + message.getData() + "\"");
            }
        });
    }

}
