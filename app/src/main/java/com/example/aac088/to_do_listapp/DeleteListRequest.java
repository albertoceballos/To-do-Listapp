package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/16/2017.
 */

public class DeleteListRequest extends StringRequest {
    private HashMap<String, String> params;
    private static final String DELETE_LIST_URL="https://albertoceballos20.000webhostapp.com/delete_list.php";

    public DeleteListRequest(String listname,String user_id, Response.Listener<String> listener){
        super(Method.POST,DELETE_LIST_URL,listener,null);
        params = new HashMap<>();
        params.put("listname",listname);
        params.put("user_id",user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
