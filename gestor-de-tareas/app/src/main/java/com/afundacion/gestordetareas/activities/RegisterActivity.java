package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.utils.MenuActivity;
import com.afundacion.gestordetareas.utils.RestClient;

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
                    client.registerUser(name, email, password);
                }
            }
        });
    }
}
