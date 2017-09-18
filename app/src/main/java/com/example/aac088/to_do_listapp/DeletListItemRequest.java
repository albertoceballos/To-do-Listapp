package com.example.aac088.to_do_listapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by aac088 on 9/16/2017.
 */

public class DeletListItemRequest extends AsyncTask<Void, Void, Void> {
    private static final String DELETE_LIST_ITEM_URL="https://albertoceballos20.000webhostapp.com/delete_list_item.php";
    private Context context;
    private String address="https://albertoceballos20.000webhostapp.com/get_task.php";
    private ListView lv;
    private String user_id;
    private String master_list_id;
    private String task;
    private ProgressDialog pd;

    public DeletListItemRequest(String task, String master_list_id,String user_id, Context context,ListView lv){
        this.master_list_id=master_list_id;
        this.user_id=user_id;
        this.task = task;
        this.context=context;
        this.lv=lv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        DeleteListItem();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent i = new Intent(context,TaskActivity.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cases","1");
        editor.putString("master_list_id",master_list_id);
        editor.putString("user_id",user_id);
        editor.commit();
        context.startActivity(i);
    }

    private void DeleteListItem(){
        InputStream is = null;
        String pass_data =null;
        String line = null;

        try{
            pass_data = URLEncoder.encode("master_list_id","UTF-8") + "=" + URLEncoder.encode(master_list_id,"UTF-8")
            + URLEncoder.encode("task","UTF-8") + "=" + URLEncoder.encode(task,"UTF-8") +
            URLEncoder.encode("user_id","UTF-8") + "=" + URLEncoder.encode(user_id,"UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        try{
            URL url = new URL(DELETE_LIST_ITEM_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
