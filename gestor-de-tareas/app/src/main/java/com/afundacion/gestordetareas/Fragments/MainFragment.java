package com.afundacion.gestordetareas.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.RestClient.RestClient;
import com.afundacion.gestordetareas.TaskData;
import com.afundacion.gestordetareas.TaskRecyclerViewAdapter;
import com.afundacion.gestordetareas.TaskViewHolder;
import com.afundacion.gestordetareas.activities.fragmentCreatiom;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TaskViewHolder viewHolder;
    private TaskRecyclerViewAdapter adapter;
    private RequestQueue queue;
    private List<TaskData> taskList;
    private RecyclerView recyclerView;
    private JsonArrayRequest request;
    private AlertDialog.Builder myBuilder;
    private AlertDialog myDialog;
    Fragment fragment=this;
    private RestClient client;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Context context= getContext();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);

        //En el activity_main.xml solo tenemos el RecycerViewer. Aquí le asignamos al objeto
        //recyclerview el recyclerviewer del xml
        this.recyclerView = view.findViewById(R.id.RecyclerView);

        this.queue = Volley.newRequestQueue(context);
        List<TaskData> listaTasks= new ArrayList<>();
        //Creaamos el floating button y lo asociamos con el fragmento de añadir tarea
        Fragment fragment = MainFragment.newInstance("Inicio");
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.frameLayout,new fragmentCreatiom());
                fr.commit();
            }
        });

        //Creamos un diálogo con el spinner y lo lanzamos
        myBuilder = new AlertDialog.Builder(context);
        myBuilder.setView(inflateDialogView());
        myDialog = myBuilder.create();
        myDialog.show();

        Runnable loadJson= new Runnable() {
            @Override
            public void run() {
                request= new JsonArrayRequest(Request.Method.GET,
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

                                //Creamos el adaptador y se lo pasamos al reciclerView
                                TaskRecyclerViewAdapter adapter= new TaskRecyclerViewAdapter(listaTasks,fragment);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));

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
        adapter= new TaskRecyclerViewAdapter(listaTasks,this);
        recyclerView.setAdapter(adapter);

        return view;


    }
    private View inflateDialogView() {
        //Aquí se recoge el layout de loading, donde está el spinner
        LayoutInflater inflater = getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.loading, null);

        return inflatedView;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Context context= getContext();
        View view= getView();
        int position;
         super.onContextItemSelected(item);
         switch (item.getItemId()){
             case 101:
                 //Toast.makeText(context,adapter.getPosition(), Toast.LENGTH_LONG).show();
                 position= adapter.getPosition();
                 recyclerView.removeViewAt(item.getGroupId());
                 adapter.deleteTask(item.getGroupId());


                 return true;
             case 102:
                 Toast.makeText(context,"marcada como competada", Toast.LENGTH_LONG).show();
                 adapter.markAsCompleted(item.getGroupId(),view);
                 position= adapter.getPosition();

                 return true;
             default:
                 return super.onContextItemSelected(item);

         }
    }


}