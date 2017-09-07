package com.example.aac088.to_do_listapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView txtview;
    private ImageView imgview;
    private String addressMaster="https://albertoceballos20.000webhostapp.com/get_master_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.to_do_list_view);
        txtview = (TextView) findViewById(R.id.online_user_txtview);
        imgview = (ImageView) findViewById(R.id.add_image_view);

        Downloader d = new Downloader(this,addressMaster,listView,1);

        d.execute();

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreateTableActivity.class));
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
