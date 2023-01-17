package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.utils.MenuActivity;
import com.afundacion.gestordetareas.utils.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreationActivity extends MenuActivity {
    private Spinner spinner;
    private EditText titulo;
    private EditText fecha;
    private EditText description;
    private TextView fechaT;
    private RestClient client;
    private Button submit;
    private Context context = this;
    private Date data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creation);

        client = RestClient.getInstance(context);

        //Vinculamos los objetos del spinner al spinner desplegable
        spinner = (Spinner) findViewById(R.id.tareas_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tareas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fecha = findViewById(R.id.rellenarFecha);
        titulo = findViewById(R.id.rellenarTitulo);

        description = findViewById(R.id.rellenarDescripcion);

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Formateamos la fecha y metemos una string a un objeto tipo calendar
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();

                try {
                    if(fecha.getText() == null){
                        data = dateFormat.parse("01/01/2001");
                    }else{
                        data = dateFormat.parse(fecha.getText().toString());
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar.setTime(data);
                // Lanzamos el metodo con la peticion
                client.submitTarea(titulo.getText().toString(), description.getText().toString(), fecha.getText().toString(), spinner.getSelectedItem().toString());


            }
        });


    }
}
