package com.example.aac088.to_do_listapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by aac088 on 9/1/2017.
 */

public class CreateTaskRequest extends StringRequest {

    private static final String CREATE_TASK_REQUEST_URL = "https://albertoceballos20.000webhostapp.com/Add_task.php";
    private Map<String,String> params;

    public CreateTaskRequest(String list_name,String task, Response.Listener<String> listener){
        super(Method.POST,CREATE_TASK_REQUEST_URL,listener,null);
        params.put("table_name",list_name);
        params.put("new task",task);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
