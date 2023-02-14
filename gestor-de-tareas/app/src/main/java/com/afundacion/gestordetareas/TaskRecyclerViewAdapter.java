package com.afundacion.gestordetareas;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestordetareas.Fragments.MainFragment;
import com.afundacion.gestordetareas.RestClient.RestClient;
import com.afundacion.gestordetareas.activities.fragmentCreatiom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskData> allthedata;
    private Activity activity;
    private TextView title, date, description;
    private int position;
    private RestClient client;
    private Context context;



    public TaskRecyclerViewAdapter(List<TaskData> dataset, Fragment fragment){
        this.allthedata= dataset;
        this.activity= activity;
        context= fragment.getContext();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_view_holder,
                parent,false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskData dataInPositionToBeRendered= allthedata.get(position);
        holder.showData(dataInPositionToBeRendered, activity);



    }

    @Override
    public int getItemCount() {
        return allthedata.size();
    }
    public void setItems(List<TaskData> tasks) {
        this.allthedata = tasks;
    }

    public  void deleteTask(int position){

        int id= allthedata.get(position).getId();

        allthedata.remove(position);
        setItems(allthedata);


        client= RestClient.getInstance(context);
        client.deleteTaskRequest(id);




    }
    public void completeTask(int position){
        int id= allthedata.get(position).getId();
        allthedata.get(position).setCompleted(true);
        setItems(allthedata);
        //notifyDataSetChanged();
        client= RestClient.getInstance(context);
        client.isCompleted(id);
    }
    public int getId(int position){

        return allthedata.get(position).getId();

    }





}
