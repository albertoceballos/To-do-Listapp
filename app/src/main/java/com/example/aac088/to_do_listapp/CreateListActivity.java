package com.example.aac088.to_do_listapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateListActivity extends AppCompatActivity {

    private EditText new_list_et;
    private Button cancel_btn, add_new_list_btn;
    private String listname;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);

        new_list_et =(EditText) findViewById(R.id.new_list_et);
        cancel_btn =(Button) findViewById(R.id.cancel_btn);
        add_new_list_btn = (Button) findViewById(R.id.add_btn);

        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getString("user_id");

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateListActivity.this,MainActivity.class));
            }
        });

        add_new_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listname = new_list_et.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(CreateListActivity.this,MainActivity.class);
                               CreateListActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateListActivity.this);
                                builder.setMessage("List Creation failed");
                                builder.setNegativeButton("Retry",null);
                                builder.create();
                                builder.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                CreateListRequest createListRequest = new CreateListRequest(listname,user_id,responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateListActivity.this);
                queue.add(createListRequest);

            }
        });
    }
}
