package com.afundacion.gestordetareas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context= this;
    private TaskViewHolder viewHolder;
    private RequestQueue queue;
    private List<TaskData> taskList;
    private RecyclerView recyclerView;
    private JsonArrayRequest request;
    private AlertDialog.Builder myBuilder;
    private AlertDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity activity=this;
        //En el activity_main.xml solo tenemos el RecycerViewer. Aquí le asignamos al objeto
        //recyclerview el recyclerviewer del xml
        this.recyclerView = findViewById(R.id.RecyclerView);
        this.queue = Volley.newRequestQueue(context);
        List<TaskData> listaTasks= new ArrayList<>();
        //Creamos un diálogo con el spinner y lo lanzamos
        myBuilder = new AlertDialog.Builder(context);
        myBuilder.setView(inflateDialogView());
        myDialog = myBuilder.create();
        myDialog.show();

        Runnable loadJson= new Runnable() {
            @Override
            public void run() {


                request= new JsonArrayRequest(Request.Method.GET,
                        "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/users/1/tasks",
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

                                //Creamos el adaptador y se lo pasamos al reciclerView
                                TaskRecyclerViewAdapter adapter= new TaskRecyclerViewAdapter(listaTasks,activity);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(activity));

                                myDialog.dismiss();//Al terminar cerramos el spinner
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
        Thread carga= new Thread(loadJson);
        carga.start();
        //Se inicia e hilo dentro de Oncreate

    }

    private View inflateDialogView() {
        //Aquí se recoge el layout de loading, donde está el spinner
        LayoutInflater inflater = getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.loading, null);

        return inflatedView;
    }


}