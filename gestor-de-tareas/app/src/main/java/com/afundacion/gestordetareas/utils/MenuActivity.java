package com.afundacion.gestordetareas.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.afundacion.gestordetareas.Fragments.MainFragment;
import com.afundacion.gestordetareas.Fragments.ManagerFragment;
import com.afundacion.gestordetareas.R;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.afundacion.gestordetareas.R;
import com.afundacion.gestordetareas.activities.fragmentCreatiom;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context = this;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigator_layout);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        menu = navigationView.getMenu();
        navigationView.getHeaderView(0);
        navigationView.bringToFront();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigator_open, R.string.navigator_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = MainFragment.newInstance("Inicio");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();
                switch (item.getItemId()) {
                    case R.id.home:
                        Fragment fragmentManager= new ManagerFragment();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.frameLayout,new ManagerFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.tasks:
                        Fragment fragmentInicio= new MainFragment();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.frameLayout,new MainFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.tasks_manager:

                        Fragment fragmentCreatiom = new fragmentCreatiom();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new fragmentCreatiom()).commit();

                        drawerLayout.close();
                        break;
                    case R.id.statistics:
                        drawerLayout.close();
                        break;
                }
                return false;

    }
}
