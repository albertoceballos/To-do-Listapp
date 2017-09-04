package com.example.aac088.to_do_listapp;

import android.content.Intent;
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
    private String task_str, list_name_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        listView = (ListView) findViewById(R.id.task_list_view);
        editText = (EditText) findViewById(R.id.task_etxt);
        imageView = (ImageView) findViewById(R.id.add_task_img_vw);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                task_str = editText.getText().toString();
                list_name_str ="";

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(TaskActivity.this,LoginActivity.class);
                                TaskActivity.this.startActivity(intent);
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

                CreateTaskRequest createTaskRequest  = new CreateTaskRequest(list_name_str,task_str,responseListener);
                RequestQueue queue = Volley.newRequestQueue(TaskActivity.this);
                queue.add(createTaskRequest);
            }
        });
    }
}
