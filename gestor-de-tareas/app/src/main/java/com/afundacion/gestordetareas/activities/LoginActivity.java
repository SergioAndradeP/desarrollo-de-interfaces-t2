package com.afundacion.gestordetareas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button botonLogin, botonRegistro;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edit_text_usuario);
        password = findViewById(R.id.edit_text_contraseña);
        botonLogin = findViewById(R.id.boton_login);
        botonRegistro = findViewById(R.id.boton_registro);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(context, RegisterActivity.class);
                context.startActivity(registro);
            }
        });

    }
}
