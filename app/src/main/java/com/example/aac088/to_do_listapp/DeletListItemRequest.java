package com.example.aac088.to_do_listapp;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/16/2017.
 */

public class DeletListItemRequest extends StringRequest{
    private static final String DELETE_LIST_ITEM_URL="https://albertoceballos20.000webhostapp.com/delete_list_item.php";
    private Map<String,String> params;

    public DeletListItemRequest(String task,String master_list_id,String user_id,Response.Listener<String> listener){
        super(Method.POST,DELETE_LIST_ITEM_URL,listener,null);
        params = new HashMap<>();
        params.put("master_list_id",master_list_id);
        params.put("task",task);
        params.put("user_id",user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
