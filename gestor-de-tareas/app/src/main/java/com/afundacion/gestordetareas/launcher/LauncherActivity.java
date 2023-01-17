package com.afundacion.gestordetareas.launcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.MainActivity;
import com.afundacion.gestordetareas.activities.LoginActivity;
import com.afundacion.gestordetareas.utils.MenuActivity;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Editad la actividad que lanza esta clase para ir probando lo que vais haciendo
        Intent loginActivity = new Intent(this, MenuActivity.class);
        startActivity(loginActivity);
    }
}
