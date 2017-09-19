package com.example.aac088.to_do_listapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView txtview;
    private ImageView imgview;
    private String addressMaster="https://albertoceballos20.000webhostapp.com/get_master_list.php";
    private String user_id, master_list_id=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.to_do_list_view);
        txtview = (TextView) findViewById(R.id.online_user_txtview);
        imgview = (ImageView) findViewById(R.id.add_image_view);

        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getString("user_id");

        txtview.setText(user_id);

        Downloader3 d = new Downloader3();
        d.execute();

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id",user_id);
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public class Downloader3 extends AsyncTask<Void,Integer,String>{
        private ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Downloading");
            pd.setMessage("Downloading... Please wait");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String data=null;
            data = downloadData();
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if(s != null){
                Parser3 p = new Parser3(s);
                p.execute();
            }else{
                Toast.makeText(MainActivity.this,"Unable to download data",Toast.LENGTH_SHORT).show();
            }
        }

        public String downloadData(){
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
                URL url = new URL(addressMaster);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                if(pass_data != null){
                    wr.write(pass_data);
                    wr.flush();
                }else{
                    Toast.makeText(MainActivity.this,"Error passing data to server", Toast.LENGTH_SHORT).show();
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

    public class Parser3 extends AsyncTask<Void,Integer,Integer>{
        private String data;
        private ProgressDialog pd;
        private ArrayList<String> list= new ArrayList<>();

        Parser3(String data){
            this.data=data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Parser");
            pd.setMessage("Parsing... Please wait");
            pd.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return parseData();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            pd.dismiss();

            if(integer==1){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        master_list_id=listView.getItemAtPosition(position).toString();
                        Intent intent = new Intent(MainActivity.this,TaskActivity.class);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("master_list_id",master_list_id);
                        editor.putString("user_id",user_id);
                        editor.commit();
                        MainActivity.this.startActivity(intent);

                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        master_list_id = listView.getItemAtPosition(position).toString();
                        Intent intent = new Intent(MainActivity.this,EditActivity.class);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("master_list_id",master_list_id);
                        editor.putString("user_id",user_id);
                        editor.commit();
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                });
            }else{
                Toast.makeText(MainActivity.this,"Unable to Parse",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("master_list_id",master_list_id);
                editor.putString("user_id",user_id);
                editor.commit();
            }
        }

        public int parseData(){
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
}
