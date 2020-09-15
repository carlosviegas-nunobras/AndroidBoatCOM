package com.prj.androidboatcom;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;
import com.prj.androidboatcom.ui.log.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ros.internal.node.client.Registrar;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Global extends Application {

    public static ArrayList<Float> gauge1Values;
    public static ArrayList<Float> copiedGauges;
    private static int max, min, highg, diff;
    public static ArrayList<Enum> types;
    public static ArrayList<String> messages;
    public static ArrayList<Boolean> resume;
    public static ArrayList<LatLng> mapCoords;

    public Global() {
        max = 10;
        min = 5;
        diff = 0;
        highg = min;
        gauge1Values = new ArrayList<Float>();
        types = new ArrayList<Enum>();
        messages = new ArrayList<String>();
        resume = new ArrayList<Boolean>();
        mapCoords = new ArrayList<LatLng>();
        Log a = LogFactory.getLog(Registrar.class);
    }


    public static void saveLog(String message, Enum type) {
        messages.add(message);
        types.add(type);
    }

    public static float updateGauge(ArrayList<Float> gaugeValues) {


        ArrayList<Float> newGauges = (ArrayList<Float>) gaugeValues.clone();
        Collections.sort(newGauges);
        float high = newGauges.get(newGauges.size() - 1);
        float low = newGauges.get(0);
        diff = (int) (high - low);


        copiedGauges =
                new ArrayList<Float>(
                        Arrays.asList(
                                Arrays.copyOfRange(
                                        ((Float[])
                                                ((ArrayList<Float>) gaugeValues.clone()).toArray()),
                                        (int) (gaugeValues.size() - diff), gaugeValues.size() - 1)));
        Collections.sort(copiedGauges);


        return copiedGauges.get((int) Math.floor(copiedGauges.size() / 2f));


    }
}
