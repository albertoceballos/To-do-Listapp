package com.example.aac088.to_do_listapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskActivity extends AppCompatActivity {
    private ListView listView;
    private EditText editText;
    private ImageView imageView;
    private String master_list_id,user_id;
    private String task_str;
    private String addressTask="https://albertoceballos20.000webhostapp.com/get_task.php";
    private String cases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        listView = (ListView) findViewById(R.id.task_list_view);
        editText = (EditText) findViewById(R.id.task_etxt);
        imageView = (ImageView) findViewById(R.id.add_task_img_vw);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        cases = prefs.getString("cases", "0");

        if(cases.equals("0")){
            Bundle bundle = getIntent().getExtras();
            master_list_id = bundle.getString("master_list_id");
            user_id = bundle.getString("user_id");
        }
        if(cases.equals("1")){
            master_list_id = prefs.getString("master_list_id", null);
            user_id = prefs.getString("user_id",null);
        }



        Downloader d = new Downloader(this,addressTask,listView,2,user_id,master_list_id);

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
                                Downloader d2 = new Downloader(TaskActivity.this,addressTask,listView,2,user_id,master_list_id);

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
}
