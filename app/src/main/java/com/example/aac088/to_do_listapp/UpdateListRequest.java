package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/19/2017.
 */

public class UpdateListRequest extends StringRequest {
    private static final String UPDATE_LIST_URL="https://albertoceballos20.000webhostapp.com/update_list.php";
    private Map<String,String> params;

    public UpdateListRequest(String listname, String new_name, String user_id, Response.Listener<String> listener){
        super(Method.POST,UPDATE_LIST_URL,listener,null);
        params = new HashMap<>();
        params.put("listname",listname);
        params.put("new_name",new_name);
        params.put("user_id",user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
