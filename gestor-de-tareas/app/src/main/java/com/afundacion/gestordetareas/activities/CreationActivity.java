package com.afundacion.gestordetareas.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreationActivity extends AppCompatActivity {
    Spinner spinner;
    EditText titulo;
    EditText fecha;
    EditText description;
    Button submit;

    Date data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creation);

        //Vinculamos los objetos del spinner al spinner desplegable
        spinner = (Spinner) findViewById(R.id.tareas_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tareas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Formateamos la fecha y metemos una string a un objeto tipo calendar
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            data = dateFormat.parse(fecha.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(data);


        titulo = findViewById(R.id.rellenarTitulo);
        description = findViewById(R.id.rellenarDescripcion);
        submit = findViewById(R.id.submit);

        


    }
}