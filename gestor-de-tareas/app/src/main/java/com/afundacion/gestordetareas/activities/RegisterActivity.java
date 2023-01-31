package com.afundacion.gestordetareas.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.RestClient.RestClient;
import com.afundacion.gestordetareas.utils.MenuActivity;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private Button boton;
    private EditText name, email, password, repeatPassword;
    private Context context = this;
    private RestClient client;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.registerActivity_nombre);
        email = findViewById(R.id.registerActivity_email);
        password = findViewById(R.id.registerActivity_contraseña);
        repeatPassword = findViewById(R.id.registerActivity_repetirContraseña);
        boton = findViewById(R.id.registerActivity_boton_submit);
        client = RestClient.getInstance(context);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(repeatPassword.getText().toString())){
                    client.registerUser(name, email, password,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(context, "Usuario registrado", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if(error.networkResponse==null){
                                        Toast.makeText(context,"No se pudo establecer la conexión",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        int serverCode = error.networkResponse.statusCode;
                                        Toast.makeText(context,"Estado de respuesta: "+serverCode,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
