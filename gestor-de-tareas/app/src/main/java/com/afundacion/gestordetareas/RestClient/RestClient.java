package com.afundacion.gestordetareas.RestClient;

import static android.content.Context.MODE_PRIVATE;
import android.view.LayoutInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.afundacion.gestordetareas.Fragments.ManagerFragment;
import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.TaskData;
import com.afundacion.gestordetareas.TaskRecyclerViewAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RestClient {

    private String URL = "";

    private Context context;

    private static RestClient singleton = null;

    private RequestQueue queue;

    private AlertDialog.Builder myBuilder;
    private AlertDialog myDialog;


    private RestClient(Context context){

        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static RestClient getInstance(Context context){
        if(singleton==null){
            singleton = new RestClient(context);
        }
        return singleton;
    }

    // Métodos que lanzan peticiones


    public void deleteTaskRequest(int id){



        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/users/2/tasks/"+id,null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "No se pudo eliminar tarea", Toast.LENGTH_LONG).show();

                    }
                });
        queue.add(request);

    }

    public void isCompleted(int id){

        JSONObject task = new JSONObject();
        try {
            task.put("completed", true);
        } catch (JSONException e) {
            throw new RuntimeException();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/users/2/tasks/"+id,task,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Tarea completada", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "No se pudo marcar la tarea como completada", Toast.LENGTH_LONG).show();

                    }
                });
        queue.add(request);

    }

    public void getTasks(ManagerFragment mf, View view){

        List<TaskData> listaTasks= new ArrayList<>();

    JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET,
                        "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/users/2/tasks",

                                null,
                                new Response.Listener<JSONArray>(){
        @Override
        public void onResponse(JSONArray response) {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject task= response.getJSONObject(i);
                    TaskData aTask= new TaskData(task);
                    listaTasks.add(aTask);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context,"Respuesta OK", Toast.LENGTH_LONG).show();
            mf.LoadRecycler(listaTasks);

        }
    },
            new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            if(error.networkResponse==null){
                Toast.makeText(context, "Server could not be reached", Toast.LENGTH_LONG).show();
            }else{
                int serverCode=error.networkResponse.statusCode;
                Toast.makeText(context,"Server KO: "+serverCode, Toast.LENGTH_LONG).show();
            }
        }
    });
                queue.add(request);
    }
};



