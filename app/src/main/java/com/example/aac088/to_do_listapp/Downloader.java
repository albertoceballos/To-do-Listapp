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
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by aac088 on 9/7/2017.
 */

public class Downloader extends AsyncTask<Void, Integer, String>{

    private Context context;
    private String address;
    private ListView lv;

    private ProgressDialog pd;
    private String user_id, master_list_id;

    private int id;

    public Downloader(Context context, String address, ListView lv, int id, String user_id, String master_list_id){
        this.context=context;
        this.address=address;
        this.lv=lv;
        this.id=id;
        this.user_id = user_id;
        this.master_list_id = master_list_id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(Void... params) {
        String data=null;
        if(id==1){
            data =downloadDataMaster();
        }
        if(id==2){
             data = downloadDataTask();
        }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //pd.dismiss();

        if(s != null){
            Parser p = new Parser(context,s,user_id,lv,id);
            p.execute();
        }else{
            Toast.makeText(context,"Unable to download data",Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadDataMaster(){
        InputStream is = null;
        String line = null;
        String pass_data=null;

        try {
            pass_data = URLEncoder.encode("user_id","UTF-8")
                    + "=" + URLEncoder.encode(user_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            if(pass_data != null){
                wr.write(pass_data);
                wr.flush();
            }else{
                Toast.makeText(context,"Error passing data to server", Toast.LENGTH_SHORT).show();
            }

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

    private String downloadDataTask(){
        InputStream is = null;
        String line = null;
        String pass_data = null;



        try {
            pass_data = URLEncoder.encode("master_list_id","UTF-8")
                    + "=" + URLEncoder.encode(master_list_id,"UTF-8");
            pass_data += "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            if(pass_data != null){
                wr.write(pass_data);
                wr.flush();
            }else{
                Toast.makeText(context,"Error passing data to server", Toast.LENGTH_LONG).show();
            }

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
