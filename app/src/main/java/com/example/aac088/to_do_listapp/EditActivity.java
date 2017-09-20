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

public class EditActivity extends AppCompatActivity {
    private EditText update_task_edittxt;
    private Button del_list_btn, update_btn;
    private String user_id,master_list_id;
    private String update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        update_task_edittxt = (EditText) findViewById(R.id.update_task_edittxt);
        del_list_btn = (Button) findViewById(R.id.del_list_btn);
        update_btn = (Button) findViewById(R.id.updt_list_btn);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        master_list_id=preferences.getString("master_list_id",null);
        user_id=preferences.getString("user_id",null);

        update_task_edittxt.setText(master_list_id);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update = update_task_edittxt.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("user_id",user_id);
                                intent.putExtras(bundle);
                                EditActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                                builder.setMessage("pass data failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                UpdateListRequest updateListRequest = new UpdateListRequest(master_list_id,update,user_id,responseListener);
                UpdateListMasterTaskRequest updateListMasterTaskRequest = new UpdateListMasterTaskRequest(master_list_id,user_id,update,responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
                queue.add(updateListMasterTaskRequest);
                queue.add(updateListRequest);
            }
        });

        del_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("user_id",user_id);
                                intent.putExtras(bundle);
                                EditActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
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
                DeleteAllListItemsRequest d = new DeleteAllListItemsRequest(master_list_id,user_id,responseListener);
                DeleteListRequest dL = new DeleteListRequest(master_list_id,user_id,responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
                queue.add(dL);
                queue.add(d);
            }
        });
    }
}
