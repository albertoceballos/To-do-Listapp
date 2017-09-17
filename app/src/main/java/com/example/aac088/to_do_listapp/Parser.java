package com.example.aac088.to_do_listapp;

/**
 * Created by aac088 on 9/7/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    private ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    private ArrayList<Model> list2 = new ArrayList<>();


    private ProgressDialog pd;
    private String user_id;
    private String master_list_id;


    public Parser(Context context, String data, String user_id, ListView lv, int id) {
        this.context = context;
        this.data=data;
        this.lv=lv;
        this.id = id;
        this.user_id=user_id;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);

        pd.setTitle("Parser");
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
        if(id==1){//Master List
            if(integer == 1){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,list);
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        master_list_id = lv.getItemAtPosition(position).toString();
                        System.out.println(master_list_id);
                        Intent intent = new Intent(context, TaskActivity.class);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("cases","0");
                        editor.commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("master_list_id",master_list_id);
                        bundle.putString("user_id",user_id);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }else{
                Toast.makeText(context,"Unable to Parse",Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("cases","1");
                editor.putString("master_list_id",master_list_id);
                editor.putString("user_id",user_id);
                editor.commit();
            }
        }
        if(id==2){//Task List
            if(integer == 1){
                modelArrayList = list2;
                customAdapter = new CustomAdapter(context,modelArrayList);
                lv.setAdapter(customAdapter);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("cases","0");
                editor.commit();



                /*
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,list);
                lv.setAdapter(adapter);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("cases","0");
                editor.commit();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });*/
            }else{
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("cases","1");
                editor.putString("master_list_id",master_list_id);
                editor.putString("user_id",user_id);
                editor.commit();
                Toast.makeText(context,"Unable to Parse",Toast.LENGTH_SHORT).show();

            }
        }

    }

    private int parseTask(){
        try {
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            list.clear();
            list2.clear();

            for(int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);

                String task = jo.getString("task");
                Model model = new Model();
                model.setSelected(false);
                model.setTask(task);
                list2.add(model);

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
