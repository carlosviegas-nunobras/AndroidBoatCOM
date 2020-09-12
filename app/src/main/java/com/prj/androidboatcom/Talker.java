package com.prj.androidboatcom;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.util.Log;
import android.widget.SeekBar;

import com.prj.androidboatcom.ui.control.ControlFragment;
import com.prj.androidboatcom.ui.log.Type;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.Publisher;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.LogManager;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Talker extends AbstractNodeMain {
    private String topic_name;

    private int leftEngine;
    private int rightEngine;
    private ControlFragment controlFragment;
    private final String E = "left_engine_power";
    private final String D = "right_engine_power";
    DateFormat DFormat;
    public Talker(ControlFragment controlFragment, URI masterURI) {
        this.controlFragment = controlFragment;
        this.topic_name = "chatter";
        leftEngine=0;
        rightEngine=0;
        DFormat = DateFormat.getTimeInstance();

        Enumeration<String> a =  LogManager.getLogManager().getLoggerNames();
        while (a.hasMoreElements()){
            System.out.println("XACAVA: " + a.nextElement());
        }
    }


    public void setLeftEngine(int leftEngine){
            this.leftEngine = leftEngine;
    }
    public void setRightEngine(int rightEngine){
        this.rightEngine = rightEngine;
    }

    public int getLeftEngine() {
        return leftEngine;
    }

    public int getRightEngine() {
        return rightEngine;
    }

    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/talker");
    }

    @Override
    public void onShutdown(Node node) {
        super.onShutdown(node);

        String message = "Controlo Desligado - " + DFormat.format(new Date());
        Global.saveLog(message, Type.CONTROL);
    }

    public void onStart(final ConnectedNode connectedNode) {
        final Publisher<std_msgs.String> publisherE = connectedNode.newPublisher(E, "std_msgs/String");
        final Publisher<std_msgs.String> publisherD = connectedNode.newPublisher(D, "std_msgs/String");

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private  JoystickView joystickView;
            private SeekBar seekBar;
            protected void setup() {
                joystickView = controlFragment.findViewById(R.id.joystickview);
                seekBar = controlFragment.findViewById(R.id.speedSlider);
                Global.saveLog("Nó "+connectedNode.getName()+": A publicar nos tópicos '"+E+"' e '"+D+"' do ROS_MASTER " + connectedNode.getMasterUri() + " " + DFormat.format(new Date()) , Type.CONTROL);
                String myStringArray[]= {"logcat","-d"};

            }

            protected void loop() throws InterruptedException {
                final std_msgs.String strE = (std_msgs.String)publisherE.newMessage();
                final std_msgs.String strD = (std_msgs.String)publisherD.newMessage();

                joystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
                    @Override
                    public void onMove(int angle, int strength) {
                        //Manipular os dados para o barco
                        setRightEngine(0);
                        setLeftEngine(0);

                        if (angle > 0 && angle <= 90) {
                            //1º Quad
                            setRightEngine( 100 - angle * 100 / 90);
                        } else if (angle >= 90 && angle <= 180){


                            //2º Quad
                            setLeftEngine((100 - angle * 100 / 90) * -1);
                        } else if (angle >= 180 && angle <= 270) {
                            //3º Quad
                            setLeftEngine(300 - angle * 100 / 90);
                        } else if (angle >= 270 && angle <= 360) {
                            //4º Quad
                            setRightEngine( (300 - angle * 100 / 90) * -1);

                        }


                    }
                });
                Integer E = Math.round(seekBar.getProgress()*(1-getLeftEngine()/100f));
                Integer D = Math.round(seekBar.getProgress()*(1-getRightEngine()/100f));

                strE.setData(E + "");
                strD.setData(D+"");

                publisherE.publish(strE);
                publisherD.publish(strD);

                Thread.sleep(99);
            }
        });
    }
}

