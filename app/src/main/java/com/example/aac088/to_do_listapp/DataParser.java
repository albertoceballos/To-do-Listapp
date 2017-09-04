package com.example.aac088.to_do_listapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aac088 on 9/4/2017.
 */

public class DataParser extends AsyncTask<Void,Void,Boolean>{
    private Context context;
    private String jsonData;
    private ListView listView;
    private String owneruser;

    private ProgressDialog progressDialog;
    private ArrayList<String> data= new ArrayList<>();

    public DataParser(Context context, String jsonData, ListView listView,String owner){
        this.context = context;
        this.jsonData = jsonData;
        this.listView = listView;
        this.owneruser=owner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Parse");
        progressDialog.setMessage("Parsing... Please wait");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    private Boolean parseDataLISTS(){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject;

            data.clear();

            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);

                String ListName = jsonObject.getString("ListName");
                String owner = jsonObject.getString("Owner");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean parseDATATASKS(){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject;

            data.clear();

            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);

                String Task = jsonObject.getString("Task");
                String owner = jsonObject.getString("Owner");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
