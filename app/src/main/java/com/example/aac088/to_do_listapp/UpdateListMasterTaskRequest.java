package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/19/2017.
 */

public class UpdateListMasterTaskRequest extends StringRequest {
    private static final String UPDATE_LIST_MASTER_URL="https://albertoceballos20.000webhostapp.com/update_master_task.php";
    private Map<String,String> params;

    public UpdateListMasterTaskRequest(String master_list_id, String user_id, String new_master, Response.Listener<String> listener){
        super(Method.POST,UPDATE_LIST_MASTER_URL,listener,null);
        params = new HashMap<>();
        params.put("master_list_id",master_list_id);
        params.put("user_id",user_id);
        params.put("new_master",new_master);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
