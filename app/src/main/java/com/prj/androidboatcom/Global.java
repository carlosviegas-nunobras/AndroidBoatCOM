package com.prj.androidboatcom;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;
import com.prj.androidboatcom.ui.log.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ros.internal.node.client.Registrar;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Global extends Application {

   public static ArrayList<Float> gauge1Values;
    public static ArrayList<Float> copiedGauges;

    public static ArrayList<Enum> types;
   public static ArrayList<String> messages;
   public static ArrayList<Boolean> resume;
   public static ArrayList<LatLng> mapCoords;
    public Global(){

        gauge1Values = new ArrayList<Float>();
        types = new ArrayList<Enum>();
        messages = new ArrayList<String>();
        resume = new ArrayList<Boolean>();
        mapCoords = new ArrayList<LatLng>();
        Log a = LogFactory.getLog(Registrar.class);
}


public static void saveLog(String message, Enum type){
        messages.add(message);
        types.add(type);
    }
public static float updateGauge(ArrayList<Float> gaugeValues){

        if(gaugeValues.size()==5) {
            gaugeValues.remove(0);
        }
            copiedGauges = (ArrayList<Float>) gaugeValues.clone();
            Collections.sort(copiedGauges);

            return copiedGauges.get((int) Math.floor(copiedGauges.size() / 2f));

}
}
