package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/1/2017.
 */

public class CreateListRequest extends StringRequest{

    private static final String CREATE_TABLE_REQUEST_URL = "https://albertoceballos20.000webhostapp.com/addto_masterlist.php";
    private Map<String,String> params;

    public CreateListRequest(String listname, String user_id, Response.Listener<String> listener){
        super(Method.POST,CREATE_TABLE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("listname",listname);
        params.put("user_id",user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
