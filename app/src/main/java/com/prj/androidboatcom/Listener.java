package com.prj.androidboatcom;


import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.prj.androidboatcom.ui.localization.LocalFragment;
import com.prj.androidboatcom.ui.monitorization.MonitorFragment;

import org.apache.commons.logging.Log;
import org.ros.internal.node.client.Registrar;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import de.nitri.gauge.Gauge;
import sensor_msgs.NavSatFix;
import std_msgs.String;

public class Listener extends AbstractNodeMain {
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

    public GraphName getDefaultNodeName() {
        return GraphName.of("android_boatcom/listener");
    }


    public void onStart(ConnectedNode connectedNode) {
        if (type == 0) {
            final Log log = connectedNode.getLog();
            Subscriber<String> subscriber = connectedNode.newSubscriber("test", "std_msgs/String");
            subscriber.addMessageListener(new MessageListener<String>() {
                public void onNewMessage(String message) {
                    Gauge g = monitorFragment.findViewById(R.id.gauge2);
                    Float val = Float.parseFloat(message.getData());
                    Global.gauge1Values.add(val);
                    if(Global.gauge1Values.size()>=10){
                        Global.gauge1Values.remove(0);
                    }
                    if (Global.gauge1Values.size() < 10) {
                        g.setValue(val);
                    } else {
                        g.setValue(Global.updateGauge(Global.gauge1Values));
                    }

                    log.info("I heard: \"" + message.getData() + "\"");
                }
            });
        } else if (type == 1) {
            Subscriber<NavSatFix> subscriber = connectedNode.newSubscriber("gps","sensor_msgs/NavSatFix");
            subscriber.addMessageListener((new MessageListener<NavSatFix>() {
                public void onNewMessage(NavSatFix message) {
                    NavSatFix gps = message;
                    LatLng pos = new LatLng(gps.getLatitude(),gps.getLongitude());
                    localFragment.updateLocation(localFragment.getMap(),pos,new PolylineOptions());
                    Global.mapCoords.add(pos);
                }
            }));
        }
    }
}
