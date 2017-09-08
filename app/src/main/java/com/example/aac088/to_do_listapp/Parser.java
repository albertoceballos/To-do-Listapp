package com.example.aac088.to_do_listapp;

/**
 * Created by aac088 on 9/7/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser extends AsyncTask<Void,Integer,Integer> {

    private Context context;
    private ListView lv;
    private String data;
    private int id;

    private ArrayList<String> list= new ArrayList<>();

    private ProgressDialog pd;

    public Parser(Context context, String data, ListView lv, int id) {
        this.context = context;
        this.data=data;
        this.lv=lv;
        this.id = id;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd.setTitle("com.example.aac088.to_do_listapp.Parser");
        pd.setMessage("Parsing data... Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        if(id == 1){
            return this.parseMaster();
        }
        if(id==2){
            return this.parseTask();
        }
        else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        pd.dismiss();

        if(integer == 1){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,list);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }else{
            Toast.makeText(context,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }
    }

    private int parseTask(){
        try {
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            list.clear();

            for(int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);

                String task = jo.getString("task");

                list.add(task);
            }
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }



    private int parseMaster(){
        try {
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            list.clear();

            for(int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);

                String listname =jo.getString("listname");

                list.add(listname);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
