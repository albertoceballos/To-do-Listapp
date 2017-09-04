package com.example.aac088.to_do_listapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aac088 on 9/4/2017.
 */

public class Connector {

    public static Object connect(String urladdress){
        try{
            URL url = new URL(urladdress);
            HttpURLConnection con =(HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);

            return con;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error: "+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: "+e.getMessage();
        }
    }
}
