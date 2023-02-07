package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.TaskData;
import com.afundacion.gestordetareas.Utils;
import com.afundacion.gestordetareas.utils.RestClient;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.CancelledKeyException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class fragmentCharts extends Fragment {

    public fragmentCharts(){}

    Context context;
    GraphView graph;
    RestClient client;
    int[] listInt = new int[7];

    public void setArraylist(List<TaskData> list){
        for(int i = 0; i < list.size(); i++){

            try {


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7));
                System.out.println("new task");

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");



                Date dateM = format.parse(list.get(i).getDate());

                String dateS = formatter.format(date);
                String dateMS = formatter.format(dateM);



                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[0]++;
                    System.out.println("entró");
                }

                date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(6));
                dateS = formatter.format(date);


                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[1]++;
                    System.out.println("entró1");
                }

                date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5));
                dateS = formatter.format(date);


                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[2]++;
                    System.out.println("entró2");
                }


                date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(4));
                dateS = formatter.format(date);
                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[3]++;
                    System.out.println("entró3");
                }

                date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3));
                dateS = formatter.format(date);

                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[4]++;
                    System.out.println("entró4");
                }

                date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2));
                dateS = formatter.format(date);
                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[5]++;
                    System.out.println("entró5");
                }

                date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
                dateS = formatter.format(date);
                if(dateS.equalsIgnoreCase(dateMS)){
                    listInt[6]++;
                    System.out.println("entró6");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(0, listInt[0]));
        points.add(new DataPoint(1, listInt[1]));
        points.add(new DataPoint(2, listInt[2]));
        points.add(new DataPoint(3, listInt[3]));
        points.add(new DataPoint(4, listInt[4]));
        points.add(new DataPoint(5, listInt[5]));
        points.add(new DataPoint(6, listInt[6]));


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points.toArray(new DataPoint[0]));
        graph.addSeries(series);




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        client = RestClient.getInstance(context);



        client.getNumberTareas(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<TaskData> lista = new ArrayList<>();
                for(int i = 0;i< response.length();i++){
                    try {
                        TaskData aTask = new TaskData(response.getJSONObject(i));
                        lista.add(aTask);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("aplicar metodo");
                setArraylist(lista);





            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se pudo encontrar tareas", Toast.LENGTH_LONG).show();

            }
        });

        for(int i = 0;i< listInt.length; i++){
            System.out.println(listInt[i]);
        }
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        graph = view.findViewById(R.id.graph);


        return view;

    }
}
