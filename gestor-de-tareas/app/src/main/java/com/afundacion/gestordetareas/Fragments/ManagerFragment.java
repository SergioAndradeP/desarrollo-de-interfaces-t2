package com.afundacion.gestordetareas.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afundacion.gestordetareas.ManagerRecyclerViewAdapter;
import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.RestClient.RestClient;
import com.afundacion.gestordetareas.TaskData;
import com.afundacion.gestordetareas.TaskRecyclerViewAdapter;
import com.afundacion.gestordetareas.TaskViewHolder;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerFragment extends Fragment {

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
    private AlertDialog.Builder myBuilder;
    private AlertDialog myDialog;
    Fragment fragment=this;
    private RestClient client;


    public ManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        Bundle args = new Bundle();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();

        View view = inflater.inflate(R.layout.fragment_manager, container, false);

        List<TaskData> listaTasks= new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        this.recyclerView = view.findViewById(R.id.RecyclerView2);
        myBuilder = new AlertDialog.Builder(context);
        myBuilder.setView(inflateDialogView());
        myDialog = myBuilder.create();
        myDialog.show();

        Runnable loadJson = new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = context.getSharedPreferences("GESTOR_DE_TAREAS", MODE_PRIVATE);
                String id = preferences.getString("id", null);
                JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET,
                        "https://63be7c54e348cb07620fda89.mockapi.io/api/v1/users/"+id+"/tasks",

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
                                ManagerRecyclerViewAdapter adapter= new ManagerRecyclerViewAdapter(listaTasks,fragment);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));


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
        myDialog.dismiss();
        


        return view;

    }



    public void LoadRecycler(List<TaskData> list){
        adapter= new TaskRecyclerViewAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }

    private View inflateDialogView() {
        //Aqu?? se recoge el layout de loading, donde est?? el spinner
        LayoutInflater inflater = getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.loading, null);

        return inflatedView;
    }

}