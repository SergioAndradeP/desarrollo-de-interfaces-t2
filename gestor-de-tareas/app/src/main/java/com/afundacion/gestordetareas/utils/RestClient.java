package com.afundacion.gestordetareas.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;
import com.afundacion.gestordetareas.Fragments.MainFragment;

import com.afundacion.gestordetareas.TaskData;
import com.afundacion.gestordetareas.Utils;

import com.afundacion.gestordetareas.activities.fragmentCharts;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RestClient {

    private String BASE_URL = "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/";

    private Context context;

    private static RestClient singleton = null;

    private RequestQueue queue;


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


    public void getNumberTareas(Response.Listener respuesta ,Response.ErrorListener error){
        SharedPreferences preferences = context.getSharedPreferences("GESTOR_DE_TAREAS", MODE_PRIVATE);
        //String id = preferences.getString("id", null);
        //System.out.println(id);
            JsonArrayRequest request= new JsonArrayRequest(
                Request.Method.GET,
                    "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/users/1/tasks",
                null,
                    respuesta,
                error);
            this.queue.add(request);

    }

    public void submitTarea(String titulo, String descripcion, String fecha, String tipo){
        JSONObject tarea = new JSONObject();
        SharedPreferences preferences = context.getSharedPreferences("GESTOR_DE_TAREAS", MODE_PRIVATE);
        String id = preferences.getString("id", null);


        try{
            tarea.put("name",titulo);
            tarea.put("description",descripcion);
            tarea.put("deadline",fecha);
            tarea.put("category",tipo);
            tarea.put("userId", id);
            tarea.put("completed", false);

        }catch (JSONException e){
            throw new RuntimeException(e);

        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+"users/"+id+"/tasks",
                tarea,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Tarea añadida" +tarea, Toast.LENGTH_LONG).show();
                    
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "No se pudo añadir la tarea" +tarea, Toast.LENGTH_LONG).show();
                    }
                }



        );

        this.queue.add(request);







    }

    public void loginUser(EditText email, EditText password){
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("email", email.getText().toString());
            requestBody.put("password", password.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/users",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String receivedToken;
                        try {
                            receivedToken = response.getString("token");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        Toast.makeText(context,"Token de sesión: "+receivedToken,Toast.LENGTH_LONG).show();
                        Intent home = new Intent(context, MainFragment.class);
                        context.startActivity(home);
                        SharedPreferences preferences = context.getSharedPreferences("GESTOR_DE_TAREAS_PREFS",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("VALID_EMAIL", email.getText().toString());
                        editor.putString("VALID_TOKEN", receivedToken);
                        editor.commit();
                        ((Activity) context).finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse==null){
                            Toast.makeText(context,"No se pudo establecer la conexión",Toast.LENGTH_LONG).show();
                        }
                        else{
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context,"Estado de respuesta: "+serverCode,Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        this.queue.add(request);
    }

    public void registerUser(EditText nombre, EditText email, EditText password, Context contexto){
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", nombre.getText().toString());
            requestBody.put("email", email.getText().toString());
            requestBody.put("password", password.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/users",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Usuario registrado", Toast.LENGTH_LONG).show();
                        ((Activity)contexto).finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse==null){
                            Toast.makeText(context,"No se pudo establecer la conexión",Toast.LENGTH_LONG).show();
                        }
                        else{
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context,"Estado de respuesta: "+serverCode,Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        this.queue.add(request);
    }
}
