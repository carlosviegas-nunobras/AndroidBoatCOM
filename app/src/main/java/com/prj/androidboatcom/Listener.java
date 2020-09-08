package com.prj.androidboatcom;


import com.prj.androidboatcom.ui.localization.LocalFragment;
import com.prj.androidboatcom.ui.monitorization.MonitorFragment;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import de.nitri.gauge.Gauge;
import sensor_msgs.NavSatFix;
import std_msgs.String;

public class Listener extends AbstractNodeMain {
    private java.lang.String value;
    private MonitorFragment monitorFragment;
    private LocalFragment localFragment;
    private int type;

    public Listener( MonitorFragment monitorFragment) {
        this.monitorFragment= monitorFragment;
        type=0;
    }
    public Listener( LocalFragment localFragment) {
        this.localFragment= localFragment;
        type=1;
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
                log.info("I heard: \"" + message.getData() + "\"");
            }
        });
    }

}
