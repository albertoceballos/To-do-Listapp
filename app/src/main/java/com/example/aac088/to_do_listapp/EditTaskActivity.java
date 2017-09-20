package com.example.aac088.to_do_listapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EditTaskActivity extends AppCompatActivity {

    private EditText task_edittext;
    private Button update_task_btn;
    private String new_task,current_task;
    private String master_list_id,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        task_edittext = (EditText) findViewById(R.id.task_edittext);
        update_task_btn = (Button) findViewById(R.id.update_task_btn);

        Bundle bundle = getIntent().getExtras();
        current_task=bundle.getString("task");
        master_list_id = bundle.getString("master_list_id");
        user_id = bundle.getString("user_id");

        task_edittext.setText(current_task);

        update_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_task=task_edittext.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(EditTaskActivity.this,TaskActivity.class);
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EditTaskActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("master_list_id",master_list_id);
                                editor.putString("user_id",user_id);
                                editor.commit();
                                EditTaskActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                                builder.setMessage("Unable to send data")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                UpdateTaskRequest taskRequest = new UpdateTaskRequest(master_list_id,current_task,user_id,new_task,responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditTaskActivity.this);
                queue.add(taskRequest);

            }
        });
    }
}
