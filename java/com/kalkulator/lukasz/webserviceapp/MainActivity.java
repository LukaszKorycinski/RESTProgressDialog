package com.kalkulator.lukasz.webserviceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncTaskResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RESTconnection rESTconnection=new RESTconnection(this,this);
        rESTconnection.execute("http://damianchodorek.com/wsexample/");
    }



    @Override
    public void processFinish(String s) {
        JSONObject json = null;
        Log.e("processFinish","string "+s);
        try {
            json = new JSONObject(s);
            ((TextView) findViewById(R.id.OutputTextView)).setText("id: " + json.optString("id") +
                                                                    " name: " + json.optString("name"));

        } catch (JSONException e) {
            Log.e("Exception","processFinish no json data");
            ((TextView) findViewById(R.id.OutputTextView)).setText("no json data"+s);
        }
     }



}
