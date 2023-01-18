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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
