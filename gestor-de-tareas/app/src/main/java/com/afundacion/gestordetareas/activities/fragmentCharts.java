package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.graphics.Color;
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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.CancelledKeyException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class fragmentCharts extends Fragment {

    public fragmentCharts(){}

    Context context;
    GraphView graph;
    RestClient client;
    int[] listInt = new int[7];

    public void setArraylist(List<TaskData> list) {
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        Date date4 = null;
        Date date5 = null;
        Date date6 = null;
        Date date7 = null;
        SimpleDateFormat formatter = null;
        Date date8= null;
        for (int i = 0; i < list.size(); i++) {

            try {


                formatter = new SimpleDateFormat("yyyy-MM-dd");
                date1 = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7));
                System.out.println("new task");

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


                Date dateM = format.parse(list.get(i).getDate());

                String dateS = formatter.format(date1);
                String dateMS = formatter.format(dateM);
                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[0]++;

                }

                date2 = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5));
                dateS = formatter.format(date2);


                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[1]++;

                }

                date3 = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(4));
                dateS = formatter.format(date3);


                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[2]++;

                }


                date4 = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3));
                dateS = formatter.format(date4);
                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[3]++;

                }

                date5 = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2));
                dateS = formatter.format(date5);

                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[4]++;

                }

                date6 = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
                dateS = formatter.format(date6);
                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[5]++;

                }

                date7 = new Date(System.currentTimeMillis());
                dateS = formatter.format(date7);
                if (dateS.equalsIgnoreCase(dateMS)) {
                    listInt[6]++;

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        //Sólo necesitamos 7 fechas
        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(date1, listInt[0]));
        points.add(new DataPoint(date2, listInt[1]));
        points.add(new DataPoint(date3, listInt[2]));
        points.add(new DataPoint(date4, listInt[3]));
        points.add(new DataPoint(date5, listInt[4]));
        points.add(new DataPoint(date6, listInt[5]));
        points.add(new DataPoint(date7, listInt[6]));



        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points.toArray(new DataPoint[]{}
        ));



        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });
        //Fijamos el espacio entre las barras a 0, para que estén pegadas
        series.setSpacing(0);

        graph.addSeries(series);

        //Formateamos las etiquetas para que no nos muestren el año
        DateFormat df = new SimpleDateFormat("dd-MM", Locale.getDefault());
        DateAsXAxisLabelFormatter aLF = new DateAsXAxisLabelFormatter(getContext(),df);
        graph.getGridLabelRenderer().setLabelFormatter(aLF);
        //Fijamos el número de etiquetas a 5, para que nos muestre las 3 del medio, que son las más relevantes
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);

        graph.getViewport().setXAxisBoundsManual(true);
        //Fijamos el mínimo al día anterior y el máximo al día posterior, para que no se vean cortadas la barras
        graph.getViewport().setMinX(date1.getTime()-TimeUnit.DAYS.toMillis(1));
        graph.getViewport().setMaxX(date7.getTime()+TimeUnit.DAYS.toMillis(1));
        graph.getGridLabelRenderer().setHumanRounding(false);
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
