package com.afundacion.gestordetareas.RestClient;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {

    private String URL = "";

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
}
