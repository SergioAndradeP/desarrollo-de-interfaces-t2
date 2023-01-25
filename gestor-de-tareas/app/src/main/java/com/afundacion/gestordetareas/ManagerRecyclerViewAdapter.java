package com.afundacion.gestordetareas;


import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestordetareas.Fragments.MainFragment;
import com.afundacion.gestordetareas.activities.fragmentCreatiom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManagerRecyclerViewAdapter extends RecyclerView.Adapter<ManagerViewHolder> {
    private List<TaskData> allthedata;
    private Activity activity;
    private TextView title, date, category;


    public ManagerRecyclerViewAdapter(List<TaskData> dataset, Fragment fragment) {
        this.allthedata = dataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_view_holder,
                parent, false);



        return new ManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerViewHolder holder, int position) {
        TaskData dataInPositionToBeRendered = allthedata.get(position);
        holder.showData(dataInPositionToBeRendered, activity);
    }



    @Override
    public int getItemCount() {
        return allthedata.size();
    }
}