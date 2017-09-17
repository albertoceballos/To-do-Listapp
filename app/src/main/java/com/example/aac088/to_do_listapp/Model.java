package com.example.aac088.to_do_listapp;

/**
 * Created by aac088 on 9/16/2017.
 */

public class Model {

    private boolean isSelected;
    private String task;

    public String getTask(){
        return task;
    }

    public void setTask(String task){
        this.task=task;
    }

    public boolean getSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected){
        isSelected=selected;
    }
}
