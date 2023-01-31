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

public class ManagerViewHolder extends RecyclerView.ViewHolder  {
    private TextView title, date, category;

    private Context context;

    private View vista;
    private AlertDialog.Builder myBuilder;
    private AlertDialog myDialog;

    public ManagerViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        category = (TextView) itemView.findViewById(R.id.category);
        date = (TextView) itemView.findViewById(R.id.date);
        title = (TextView) itemView.findViewById(R.id.title);
        vista = (View) itemView.findViewById(R.id.holder2);


    }

    public void showData(TaskData data, Activity activity) {

        //Se muestran los datos y se coloca el listenner en la vista. Al pulsar imagen o descripción
        //se creará el Intent
        title.setText("Task: " + (data.getTitle()));
        date.setText("Deadline: " + data.getDate());
        category.setText("Category: " + data.getCategory());



    }
}
