package com.afundacion.gestordetareas.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.afundacion.gestordetareas.MainActivity;
import com.afundacion.gestordetareas.activities.RegisterActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

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
                        Intent home = new Intent(context, MainActivity.class);
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
