package com.afundacion.gestordetareas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private TextView title,date,description;

    private Context context;

    private View vista;
    private AlertDialog.Builder myBuilder;
    private AlertDialog myDialog;
    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        context= itemView.getContext();
        description= (TextView) itemView.findViewById(R.id.description);
        date= (TextView) itemView.findViewById(R.id.date);
        title= (TextView) itemView.findViewById(R.id.title);
        vista = (View) itemView.findViewById(R.id.holder);
        itemView.setOnCreateContextMenuListener(this);
        //La vista es holder, el constraintlayout de logo_view_holder.xml

    }

    public void showData(TaskData data, Activity activity){


        title.setText("Task: "+(data.getTitle()));
        date.setText("Deadline: "+data.getDate());
        description.setText("Description: "+data.getDescription());
        if(data.getCompleted()){
            title.setBackgroundColor(0xFF4CAF50);
            date.setBackgroundColor(0xFF4CAF50);
            description.setBackgroundColor(0xFF4CAF50);
        }
        if(!Utils.DateIsFuture(data.getDate()) && !data.getCompleted()){
            title.setBackgroundColor(0xFF8D0432);
            date.setBackgroundColor(0xFF8D0432);
            description.setBackgroundColor(0xFF8D0432);

        }

        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(getAdapterPosition(),101,0,"Borrar");
        contextMenu.add(getAdapterPosition(),102,1,"Marcar como completada");
    }

}

