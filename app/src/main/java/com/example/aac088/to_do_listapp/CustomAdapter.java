package com.example.aac088.to_do_listapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by aac088 on 9/16/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<Model> modelArrayList;
    private String addressTask="https://albertoceballos20.000webhostapp.com/get_task.php";
    private ListView lv;
    private String user_id, master_list_id;

    public CustomAdapter(Context context, ListView lv,String user_id, String master_list_id, ArrayList<Model> modelArrayList){
        this.context=context;
        this.modelArrayList=modelArrayList;
        this.lv=lv;
        this.user_id=user_id;
        this.master_list_id=master_list_id;
    }

    public int getViewTypeCount(){
        return getCount();
    }

    public int getItemViewType(int position){
        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item,null,true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.task = (TextView) convertView.findViewById(R.id.task_textview);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.task.setText(modelArrayList.get(position).getTask());

        holder.checkBox.setChecked(modelArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.task_textview);
                Integer pos = (Integer) holder.checkBox.getTag();

                Toast.makeText(context, "Checkbox "+pos+" clicked!",Toast.LENGTH_SHORT).show();

                if(modelArrayList.get(pos).getSelected()){

                   // modelArrayList.get(pos).setSelected(false);

                    /*
                    */
                }else{

                    modelArrayList.get(pos).setSelected(true);
                    DeletListItemRequest d = new DeletListItemRequest(modelArrayList.get(pos).getTask(),master_list_id,user_id,context,lv);
                    //modelArrayList.remove(modelArrayList.get(pos));
                    //notifyDataSetChanged();
                    d.execute();


                    /*Downloader d2 = new Downloader(context,addressTask,lv,2,user_id,master_list_id);
                    d2.execute();*/
                }
            }
        });

        return convertView;
    }

    private class ViewHolder{
        protected CheckBox checkBox;
        private TextView task;
    }
}
