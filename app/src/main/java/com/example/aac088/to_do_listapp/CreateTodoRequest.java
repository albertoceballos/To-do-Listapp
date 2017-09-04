package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/1/2017.
 */

public class CreateTodoRequest extends StringRequest{

    private static final String CREATE_TABLE_REQUEST_URL = "https://albertoceballos20.000webhostapp.com/Create_table.php";
    private Map<String,String> params;

    public CreateTodoRequest(String list_name, Response.Listener<String> listener){
        super(Method.POST,CREATE_TABLE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("table_name",list_name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
