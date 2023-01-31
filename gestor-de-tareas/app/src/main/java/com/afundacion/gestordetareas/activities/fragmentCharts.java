package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.TaskData;
import com.afundacion.gestordetareas.Utils;
import com.afundacion.gestordetareas.utils.RestClient;
import com.android.volley.Response;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.CancelledKeyException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class fragmentCharts extends Fragment {

    public fragmentCharts(){}

    Context context;
    GraphView graph;
    RestClient client;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        client = RestClient.getInstance(context);
        Utils utils = new Utils();
        int listInt[] = new int[7];


        client.getNumberTareas(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        TaskData aTask = new TaskData(response.getJSONObject(i));
                        String fecha[] = aTask.getDate().split("/",3);
                        Calendar cal = Calendar.getInstance();

                        Calendar cFecha = utils.dateFormat(aTask.getDate());

                        cal.add(Calendar.DAY_OF_MONTH, -7);

                        if(cal.compareTo(cFecha) == 0){
                            listInt[0]++;
                        }

                        cal.add(Calendar.DAY_OF_MONTH, +1);
                        if(cal.compareTo(cFecha) == 0){
                            listInt[1]++;
                        }


                        cal.add(Calendar.DAY_OF_MONTH, +1);
                        if(cal.compareTo(cFecha) == 0){
                            listInt[2]++;
                        }

                        cal.add(Calendar.DAY_OF_MONTH, +1);
                        if(cal.compareTo(cFecha) == 0){
                            listInt[3]++;
                        }
                        cal.add(Calendar.DAY_OF_MONTH, +1);
                        if(cal.compareTo(cFecha) == 0){
                            listInt[4]++;
                        }

                        cal.add(Calendar.DAY_OF_MONTH, +1);
                        if(cal.compareTo(cFecha) == 0){
                            listInt[5]++;
                        }
                        cal.add(Calendar.DAY_OF_MONTH, +1);
                        if(cal.compareTo(cFecha) == 0){
                            listInt[6]++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        graph = view.findViewById(R.id.graph);

        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(listInt[0], 0));
        points.add(new DataPoint(listInt[1], 1));
        points.add(new DataPoint(listInt[2], 2));
        points.add(new DataPoint(listInt[3], 3));
        points.add(new DataPoint(listInt[4], 4));
        points.add(new DataPoint(listInt[5], 5));
        points.add(new DataPoint(listInt[6], 6));


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points.toArray(new
                DataPoint[points.size()]));
        graph.addSeries(series);

        return view;

    }
}
