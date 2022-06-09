package com.example.cryptoportfolioapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    public static void getJSON(String url, final ReadDataHandler rdh){
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response = "";

                try{

                    URL link = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) link.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String row;

                    while((row = br.readLine()) != null){
                        response += row + '\n';
                    }

                    br.close();
                    con.disconnect();

                }catch (Exception e){e.printStackTrace();}
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);
            }
        };

        task.execute(url);
    }

}