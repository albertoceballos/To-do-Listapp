package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aac088 on 9/19/2017.
 */

public class UpdateTaskRequest extends StringRequest {
    private static final String UPDATE_TASK_URL="https://albertoceballos20.000webhostapp.com/update_task.php";
    private Map<String, String> params;

    public UpdateTaskRequest(String master_list_id, String task, String user_id, String new_task, Response.Listener<String> listener){
        super(Method.POST,UPDATE_TASK_URL,listener,null);
        params = new HashMap<>();
        params.put("master_list_id",master_list_id);
        params.put("task",task);
        params.put("user_id",user_id);
        params.put("new_task",new_task);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
