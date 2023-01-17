package com.afundacion.gestordetareas.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {

    private String BASE_URL = "";

    private Context context;

    private static RestClient singleton = null;

    private RequestQueue queue;


    private RestClient(Context context){
        this.context = context;
    }

    public static RestClient getInstance(Context context){
        if(singleton==null){
            singleton = new RestClient(context);
        }
        return singleton;
    }

    // Métodos que lanzan peticiones

    public void submitTarea(String titulo, String descripcion, String fecha, String tipo){
        JSONObject tarea = new JSONObject();

        try{
            tarea.put("title",titulo);
            tarea.put("description",descripcion);
            tarea.put("date",fecha);
            tarea.put("type",tipo);

        }catch (JSONException e){
            throw new RuntimeException(e);

        }
        JsonObjectRequestWithAuthentication request = new JsonObjectRequestWithAuthentication(
                Request.Method.POST,
                "url",
                tarea,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, context



        );

        this.queue.add(request);







    }
}
