package com.example.android.sunshine.app.data;

/**
 * Created by Eric Johnson on 9/6/2014.
 */
public class Model_Weather {
    String name = "list";
    String fname = "list";
    String OWM_LIST = "list";
    String OWM_WEATHER = "weather";
    String OWM_TEMPERATURE = "temp";
    String OWM_MAX = "max";
    String OWM_MIN = "min";
    String OWM_DATETIME = "dt";
    String OWM_DESCRIPTION = "main";
    public Model_Weather(String name, String fname){
        this.name = name;
        this.fname = fname;
    }
}
