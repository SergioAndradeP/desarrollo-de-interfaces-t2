package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.RestClient.RestClient;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private TextView textoRegistro;
    private Button botonLogin;
    private Context context = this;
    private RestClient client;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.edit_text_email);
        password = findViewById(R.id.edit_text_contraseña);
        botonLogin = findViewById(R.id.boton_login);
        textoRegistro = findViewById(R.id.texto_registro);
        client = RestClient.getInstance(context);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.loginUser(email, password);
            }
        });

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(context, RegisterActivity.class);
                context.startActivity(registro);
            }
        });
    }
}
