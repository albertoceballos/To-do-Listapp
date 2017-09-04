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

/**
 * Created by aac088 on 9/4/2017.
 */

public class Downloader extends AsyncTask<Void,Void,String>{
    private Context context;
    private String urlAddress;
    private ListView listView;

    private ProgressDialog progressDialog;

    public Downloader(Context context,String urlAddress, ListView listView){
        this.context=context;
        this.urlAddress=urlAddress;
        this.listView=listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Retrieve");
        progressDialog.setMessage("Retrieving... Please Wait");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        progressDialog.dismiss();
        if(jsonData.startsWith("Error")){
            Toast.makeText(context,"Unsuccessful" + jsonData, Toast.LENGTH_SHORT).show();
        }else{

        }
    }

    private String downloadData(){
        Object connection = Connector.connect(urlAddress);
        if(connection.toString().startsWith("Error")){
            return connection.toString();
        }

        try{
            HttpURLConnection con = (HttpURLConnection) connection;

            InputStream inputStream = new BufferedInputStream(con.getInputStream());
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuffer jsonData = new StringBuffer();

            while((line=bufferedReader.readLine())!= null){
                jsonData.append(line+"\n");
            }

            bufferedReader.close();
            inputStream.close();

            return jsonData.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error"+e.getMessage();
        }
    }
}
