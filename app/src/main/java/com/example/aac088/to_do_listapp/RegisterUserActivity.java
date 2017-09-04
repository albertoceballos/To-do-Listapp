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

public class RegisterUserActivity extends AppCompatActivity {

    //Creates the components
    private Button regBut;
    private EditText email,pass,confirm_pass;
    private String email_user,password,confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Initiates the components
        regBut = (Button) findViewById(R.id.reg_but);
        email = (EditText) findViewById(R.id.email_text);
        confirm_pass = (EditText) findViewById(R.id.confirm_pass_field);
        pass = (EditText) findViewById(R.id.password_field);


        //Click Listeners
        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Registration(v);
                email_user = email.getText().toString();
                password = pass.getText().toString();
                confirm_password = confirm_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(RegisterUserActivity.this,LoginActivity.class);
                                RegisterUserActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUserActivity.this);
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

                RegisterRequest registerRequest = new RegisterRequest(email_user,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterUserActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}

