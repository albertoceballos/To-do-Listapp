package com.example.aac088.to_do_listapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

public class TaskActivity extends AppCompatActivity {
    private ListView listView;
    private EditText editText;
    private ImageView imageView;
    private String master_list_id,user_id;
    private String task_str;
    private String addressTask="https://albertoceballos20.000webhostapp.com/get_task.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        listView = (ListView) findViewById(R.id.task_list_view);
        editText = (EditText) findViewById(R.id.task_etxt);
        imageView = (ImageView) findViewById(R.id.add_task_img_vw);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        master_list_id = preferences.getString("master_list_id",null);
        user_id = preferences.getString("user_id",null);

        Downloader2 d = new Downloader2();
        d.execute();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                task_str = editText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Downloader2 d2 = new Downloader2();
                                d2.execute();

                                editText.setText("");
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                                builder.setMessage("Add new task failed");
                                builder.setNegativeButton("Retry",null);
                                builder.create();
                                builder.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                CreateTaskRequest createTaskRequest  = new CreateTaskRequest(master_list_id,task_str,user_id,responseListener);
                RequestQueue queue = Volley.newRequestQueue(TaskActivity.this);
                queue.add(createTaskRequest);
            }
        });
    }

    public class Downloader2 extends AsyncTask<Void, Integer,String>{

        private ProgressDialog pd;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TaskActivity.this);
            pd.setTitle("Donwloading");
            pd.setMessage("Downloading...Please wait");
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
                Parser2 p = new Parser2(s);
                p.execute();
            }else{
                Toast.makeText(TaskActivity.this,"Unable to download data",Toast.LENGTH_SHORT).show();
            }
        }

        private String downloadData(){
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
                URL url = new URL(addressTask);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();

                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                if(pass_data != null){
                    wr.write(pass_data);
                    wr.flush();
                }else{
                    Toast.makeText(TaskActivity.this,"Error passing data to server", Toast.LENGTH_LONG).show();
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

    public class Parser2 extends AsyncTask<Void,Integer,Integer>
    {
        private String data;

        private ArrayList<Model> modelArrayList;
        private CustomAdapter2 customAdapter2;
        private ArrayList<Model> list2 = new ArrayList<>();
        private ProgressDialog pd;

        public Parser2(String data){
            this.data=data;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TaskActivity.this);
            pd.setTitle("Parser");
            pd.setMessage("Downloading...Please wait");
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
                modelArrayList = list2;
                customAdapter2 = new CustomAdapter2(modelArrayList);
                listView.setAdapter(customAdapter2);
            }else{
                Toast.makeText(TaskActivity.this,"Unable to Parse",Toast.LENGTH_SHORT).show();
            }
        }

        private int parseData(){
            try {
                JSONArray ja = new JSONArray(data);
                JSONObject jo = null;

                list2.clear();

                for(int i=0;i<ja.length();i++){
                    jo = ja.getJSONObject(i);

                    String task = jo.getString("task");
                    Model model = new Model();
                    model.setSelected(false);
                    model.setTask(task);
                    list2.add(model);

                }
                return 1;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public class CustomAdapter2 extends BaseAdapter{
        private  ArrayList<Model> modelArrayList;

        public CustomAdapter2(ArrayList<Model> modelArrayList){
            this.modelArrayList=modelArrayList;
        }

        public int getViewTypeCount(){
            return getCount();
        }

        public int getItemViewType(int position){
            return position;
        }

        @Override
        public int getCount() {
            return modelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return modelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CustomAdapter2.ViewHolder2 holder;

            if(convertView == null){
                holder = new CustomAdapter2.ViewHolder2();
                LayoutInflater inflater = (LayoutInflater) TaskActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lv_item,null,true);

                holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
                holder.task = (TextView) convertView.findViewById(R.id.task_textview);

                convertView.setTag(holder);
            }else{
                holder = (CustomAdapter2.ViewHolder2) convertView.getTag();
            }

            holder.task.setText(modelArrayList.get(position).getTask());

            holder.checkBox.setChecked(modelArrayList.get(position).getSelected());

            holder.checkBox.setTag(R.integer.btnplusview, convertView);
            holder.checkBox.setTag(position);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                    TextView tv = (TextView) tempview.findViewById(R.id.task_textview);
                    Integer pos = (Integer) holder.checkBox.getTag();

                    Toast.makeText(TaskActivity.this, "Checkbox "+pos+" clicked!",Toast.LENGTH_SHORT).show();

                    if(modelArrayList.get(pos).getSelected()){

                        // modelArrayList.get(pos).setSelected(false);

                    /*
                    */
                    }else{

                        modelArrayList.get(pos).setSelected(true);

                        Response.Listener<String> responseListener = new Response.Listener<String>(){

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if(success){
                                        Toast.makeText(TaskActivity.this, "THE OPERATION WAS SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                                        builder.setMessage("Registration failed");
                                        builder.setNegativeButton("Retry",null);
                                        builder.create();
                                        builder.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        String task = modelArrayList.get(pos).getTask().toString();
                        modelArrayList.remove(modelArrayList.get(pos));
                        notifyDataSetChanged();
                        DeletListItemRequest d = new DeletListItemRequest(task,master_list_id,user_id,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(TaskActivity.this);
                        queue.add(d);
                    }
                }
            });

            return convertView;
        }

        private class ViewHolder2{
            protected CheckBox checkBox;
            private TextView task;
        }
    }
}
