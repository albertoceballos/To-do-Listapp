package com.example.aac088.to_do_listapp;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/16/2017.
 */

public class DeleteAllListItemsRequest extends StringRequest {
    private HashMap<String, String> params;
    private static final String DELETE_ALL_ITEMS_URL="https://albertoceballos20.000webhostapp.com/delete_all_list_items.php";

    public DeleteAllListItemsRequest(String master_list_id,String user_id, Response.Listener<String> listener){
        super(Method.POST,DELETE_ALL_ITEMS_URL,listener,null);
        params = new HashMap<>();
        params.put("master_list_id",master_list_id);
        params.put("user_id",user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
