package com.prj.androidboatcom;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.util.Log;
import android.widget.SeekBar;

import com.prj.androidboatcom.ui.notifications.ControlFragment;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import javax.security.auth.x500.X500Principal;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class TalkerC extends AbstractNodeMain {
    private String topic_name;

    private int leftEngine;
    private int rightEngine;
    private ControlFragment controlFragment;
    public TalkerC(ControlFragment controlFragment) {
        this.controlFragment = controlFragment;
        this.topic_name = "chatter";

    }

    public TalkerC(String topic) {
        this.topic_name = topic;
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

    public void onStart(ConnectedNode connectedNode) {
        final Publisher<std_msgs.Int16> publisherE = connectedNode.newPublisher(this.topic_name, "std_msgs/Int16");
        final Publisher<std_msgs.Int16> publisherD = connectedNode.newPublisher(this.topic_name, "std_msgs/Int16");

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private  JoystickView joystickView;
            private SeekBar seekBar;
            protected void setup() {
                joystickView = controlFragment.findViewById(R.id.joystickview);
                seekBar = controlFragment.findViewById(R.id.speedSlider);

            }

            protected void loop() throws InterruptedException {
                final std_msgs.Int16 strE = (std_msgs.Int16)publisherE.newMessage();
                final std_msgs.Int16 strD = (std_msgs.Int16)publisherD.newMessage();

                joystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
                    @Override
                    public void onMove(int angle, int strength) {
                        //Manipular os dados para o barco
                        setLeftEngine(0);
                        setRightEngine(0);

                        if (angle >= 0 && angle <= 90) {
                            //1º Quad

                            setRightEngine( 100 - angle * 100 / 90);
                        } else if (angle >= 90 && angle <= 180){


                            //2º Quad
                            setLeftEngine(100 - angle * 100 / 90 * -1);
                        } else if (angle >= 180 && angle <= 270) {
                            //3º Quad
                            setLeftEngine(300 - angle * 100 / 90);
                        } else if (angle >= 270 && angle <= 360) {
                            //4º Quad
                            setRightEngine( 300 - angle * 100 / 90 * -1);

                        }

                        Log.d("MACACOS", "Angle: " + angle);

                    }
                });
                Integer E = seekBar.getProgress()*(1-getLeftEngine()/100);
                Integer D = seekBar.getProgress()*(1-getRightEngine()/100);

                strE.setData(E.shortValue());
                strD.setData(D.shortValue());

                publisherE.publish(strE);
                publisherD.publish(strD);

                Thread.sleep(99);
            }
        });
    }
}

