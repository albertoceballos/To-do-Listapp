package com.example.aac088.to_do_listapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aac088 on 9/7/2017.
 */

public class Downloader extends AsyncTask<Void, Integer, String>{

    private Context context;
    private String address;
    private ListView lv;

    private ProgressDialog pd;

    private int id;

    public Downloader(Context context, String address, ListView lv, int id){
        this.context=context;
        this.address=address;
        this.lv=lv;
        this.id=id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fetching Data... Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String data=downloadData();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();

        if(s != null){
            Parser p = new Parser(context,s,lv,id);
            p.execute();
        }else{
            Toast.makeText(context,"Unable to download data",Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData(){
        InputStream is = null;
        String line = null;

        try {
            URL url = new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuffer sb = new StringBuffer();

            if(br != null){
                while((line=br.readLine()) != null){
                    sb.append(line+"\n");
                }
            }else{
                return null;
            }

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
