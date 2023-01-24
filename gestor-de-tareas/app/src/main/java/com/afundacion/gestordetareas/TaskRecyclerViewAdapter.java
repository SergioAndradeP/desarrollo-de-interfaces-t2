package com.afundacion.gestordetareas;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskData> allthedata;
    private Activity activity;
    private TextView textView;


    public TaskRecyclerViewAdapter(List<TaskData> dataset, Fragment fragment){
        this.allthedata= dataset;
        this.activity= activity;
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
    public  void deleteTask(int position){

    allthedata.remove(position);
    notifyDataSetChanged();



    }


}
