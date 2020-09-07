package com.prj.androidboatcom;


import android.content.res.Resources;

import com.prj.androidboatcom.ui.home.MonitorFragment;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import de.nitri.gauge.Gauge;
import std_msgs.String;

public class Listener extends AbstractNodeMain {
    private java.lang.String value;
    Resources res;
    private MonitorFragment monitorFragment;
    public Listener( MonitorFragment monitorFragment) {
        value="";
        this.monitorFragment= monitorFragment;

    }
    public Listener(){

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
                      Gauge g = monitorFragment.findViewById(R.id.gauge2);
             g.setValue(Float.parseFloat(message.getData()));
                System.out.println("bish"+message.getData());
                log.info("I heard: \"" + message.getData() + "\"");
            }
        });
    }

}
