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
import com.afundacion.gestordetareas.utils.RestClient;
import com.android.volley.Response;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        JSONObject tareas7dias = new JSONObject();
        JSONArray cadaDia = new JSONArray();
        tareas7dias = client.getNumberTareas(     new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tareas7dias = response;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            cadaDia = tareas7dias.getJSONArray("tareas");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        graph = view.findViewById(R.id.graph);

        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(0, 1));
        points.add(new DataPoint(1, 5));
        points.add(new DataPoint(2, 3));

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points.toArray(new
                DataPoint[points.size()]));
        graph.addSeries(series);

        return view;

    }
}
