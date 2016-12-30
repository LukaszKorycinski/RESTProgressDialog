package com.kalkulator.lukasz.webserviceapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lukasz on 2016-12-30.
 */

public class RESTconnection extends AsyncTask<String, Void, String> {

    private ProgressDialog dialog;
    private AsyncTaskResponse delegate;

    public RESTconnection(Activity aC, AsyncTaskResponse de) {//constructor, we need activity for progresDialog
        dialog = new ProgressDialog(aC);
        delegate = de;
    }

    protected void onPreExecute() {
        // wyświetlamy okienko dialogowe każące czekać
        dialog.setMessage("Czekaj...");
        dialog.show();
    }



    protected String doInBackground(String... inUrl)
    {
        try {

            URL url = new URL(inUrl[0]);
            URLConnection connection = url.openConnection();

            // pobranie danych do InputStream
            InputStream in = new BufferedInputStream(
                    connection.getInputStream());

            // konwersja InputStream na String
            // wynik będzie przekazany do metody onPostExecute()
            String out=streamToString(in);
            Log.e("Log","doInBackground"+out);
            return out;

        } catch (Exception e) {
            // obsłuż wyjątek
            Log.e("Exception","doInBackground");
            return null;
        }
    }


    protected void onPostExecute(String result) {
        if(dialog.isShowing())
            dialog.dismiss();
        Log.e("Log","onPostExecute"+result);
        delegate.processFinish(result);
    }




    // konwersja z InputStream do String
    private static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            reader.close();

        } catch (IOException e) {
            // obsłuż wyjątek
            Log.d(MainActivity.class.getSimpleName(), e.toString());
        }

        return stringBuilder.toString();
    }

}
