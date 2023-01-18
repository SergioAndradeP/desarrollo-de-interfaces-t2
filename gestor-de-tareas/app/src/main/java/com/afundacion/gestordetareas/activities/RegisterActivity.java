package com.afundacion.gestordetareas.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.utils.MenuActivity;

public class RegisterActivity extends AppCompatActivity {
    private Button boton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        boton = findViewById(R.id.registerActivity_boton_submit);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
