package com.afundacion.gestordetareas.RestClient;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.afundacion.gestordetareas.MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {

    private String URL = "https://63be7c54e348cb07620fda89.mockapi.io/api/v1";

    private String MOCK_TOKEN = "7EW878QEE4E5DF5";

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

    public void loginUser(EditText email, EditText password, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener){

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL + "/users?email="+email.getText().toString(),
                null,
                listener,
                errorListener
        );
        this.queue.add(request);
    }

    public void registerUser(EditText nombre, EditText email, EditText contrasena, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", nombre.getText().toString());
            requestBody.put("email", email.getText().toString());
            requestBody.put("password", contrasena.getText().toString());
            requestBody.put("token", MOCK_TOKEN);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL + "users",
                requestBody,
                listener,
                errorListener
        );
        this.queue.add(request);
    }
}
