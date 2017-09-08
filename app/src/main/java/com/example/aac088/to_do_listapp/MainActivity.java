package com.example.aac088.to_do_listapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView txtview;
    private ImageView imgview;
    private String addressMaster="https://albertoceballos20.000webhostapp.com/get_master_list.php";
    private String user_id, master_list_id;

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

        Downloader d = new Downloader(this,addressMaster,listView,1,user_id,master_list_id);

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



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
