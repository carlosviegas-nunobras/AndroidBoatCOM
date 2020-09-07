package com.prj.androidboatcom.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prj.androidboatcom.Listener;
import com.prj.androidboatcom.ListenerC;
import com.prj.androidboatcom.R;

import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import org.ros.rosjava_tutorial_pubsub.Talker;

import java.net.URI;

import io.github.controlwear.virtual.joystick.android.JoystickView;


public class ControlFragment extends RosActivity {
    Talker c;
    ListenerC listener;

    private int leftEngine;
    private int rightEngine;
    public ControlFragment() {
        super("PRJ", "PRJ", URI.create("http://192.168.1.87:11311"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_control);

        listener= new ListenerC(this);    }

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
    @Override
    public void init(NodeMainExecutor nodeMainExecutor) {
//        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(),getMasterUri());
//        nodeConfiguration.setNodeName("testing");
//        nodeMainExecutor.execute(listener,nodeConfiguration);

        JoystickView joystickView = findViewById(R.id.joystickview);
        joystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                SeekBar seekBar = findViewById(R.id.speedSlider);

                //Manipular os dados para o barco
                setLeftEngine(0);
                setRightEngine(0);

                if (angle >= 0 && angle <= 90) {
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
                    setRightEngine( (300 - angle * 100 / 90 )* -1);

                }
                Integer E = Math.round(seekBar.getProgress()*(1-getLeftEngine()/100f));
                Integer D = Math.round(seekBar.getProgress()*(1-getRightEngine()/100f));

                Log.d("CASCO", "SPEED: "+ seekBar.getProgress());
                Log.d("CASCO","E: " + E);
                Log.d("CASCO","D: " + D);

            }
        });


    }
}