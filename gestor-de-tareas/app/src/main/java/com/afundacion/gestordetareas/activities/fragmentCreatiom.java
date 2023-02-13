package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.utils.MenuActivity;
import com.afundacion.gestordetareas.utils.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class fragmentCreatiom extends Fragment {

    public fragmentCreatiom(){}
    private Spinner spinner;
    private EditText titulo;
    private EditText fecha;
    private EditText description;
    private TextView fechaT;
    private RestClient client;
    private Button submit;
    private Date data;
    private boolean correctDate = true;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_creation, container, false);
        context = getContext();
        client = RestClient.getInstance(context);

        //Vinculamos los objetos del spinner al spinner desplegable
        spinner = (Spinner) view.findViewById(R.id.tareas_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.tareas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fecha = view.findViewById(R.id.rellenarFecha);
        titulo = view.findViewById(R.id.rellenarTitulo);

        description = view.findViewById(R.id.rellenarDescripcion);

        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Formateamos la fecha y metemos una string a un objeto tipo calendar
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();

                try {
                    if(fecha.getText() == null){
                        data = dateFormat.parse("01-01-2001");
                    }else{
                        correctDate = true;
                        data = dateFormat.parse(fecha.getText().toString());
                    }

                } catch (ParseException e) {
                    correctDate = false;
                    Toast.makeText(context,"Fecha erronea", Toast.LENGTH_LONG).show();
                }
                if(correctDate) {
                    calendar.setTime(data);
                    // Lanzamos el metodo con la peticion
                    client.submitTarea(titulo.getText().toString(), description.getText().toString(), fecha.getText().toString(), spinner.getSelectedItem().toString());
                }


            }
        });
        return view;

    }
}
