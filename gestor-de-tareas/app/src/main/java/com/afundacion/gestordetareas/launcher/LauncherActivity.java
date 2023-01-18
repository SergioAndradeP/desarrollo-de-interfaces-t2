package com.afundacion.gestordetareas.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.activities.LoginActivity;
import com.afundacion.gestordetareas.activities.fragmentCreatiom;
import com.afundacion.gestordetareas.utils.MenuActivity;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Editad la actividad que lanza esta clase para ir probando lo que vais haciendo
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);

       /* SharedPreferences preferences = getSharedPreferences("GESTOR_DE_TAREAS_PREFS",MODE_PRIVATE);
        String email = preferences.getString("VALID_EMAIL", null);
        if(email==null) {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }
        else{
            Intent homeActivity = new Intent(this, MainActivity.class);
            startActivity(homeActivity);
        }*/
    }
}
