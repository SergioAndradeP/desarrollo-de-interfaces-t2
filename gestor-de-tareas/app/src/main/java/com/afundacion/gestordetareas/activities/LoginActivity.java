package com.afundacion.gestordetareas.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.RestClient.RestClient;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                client.loginUser(email, password, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                String receivedPassword, receivedToken;
                                try {
                                    receivedPassword = response.getJSONObject(0).getString("password");
                                    receivedToken = response.getJSONObject(0).getString("token");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                if(receivedPassword.equals(password.getText().toString())){
                                    Intent main = new Intent(context, RegisterActivity.class);
                                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(main);
                                    SharedPreferences preferences = context.getSharedPreferences("GESTOR_DE_TAREAS",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("VALID_EMAIL", email.getText().toString());
                                    editor.putString("VALID_TOKEN", receivedToken);
                                    editor.commit();
                                    finish();
                                }
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
