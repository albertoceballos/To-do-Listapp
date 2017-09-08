package com.example.aac088.to_do_listapp;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    //creates components
    private TextView newuser_txt,forgot_pass_txt,connector_txt;
    private Button login_but;
    private EditText user_txt, pass_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initiate components
        newuser_txt = (TextView) findViewById(R.id.new_user_red_txt);
        forgot_pass_txt = (TextView) findViewById(R.id.forgot_pass);
        login_but = (Button) findViewById(R.id.login_but);
        user_txt = (EditText) findViewById(R.id.user_txt);
        pass_txt = (EditText) findViewById(R.id.pass_txt);
        connector_txt = (TextView) findViewById(R.id.Connection_check_txtView);

        //Listeners
        newuser_txt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterUserActivity.class));
            }
        });
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username_str = user_txt.getText().toString();
                String password_str = pass_txt.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("user_id",username_str);
                                intent.putExtras(bundle);
                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Username or Password Incorrect")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(username_str,password_str,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        //DB Connectivity
       /* ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            connector_txt.setVisibility(View.INVISIBLE);
        }else{
            login_but.setEnabled(false);
            newuser_txt.setEnabled(false);
            forgot_pass_txt.setEnabled(false);
        }*/

        forgot_pass_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Activity and Methods to create
            }
        });
    }
}
