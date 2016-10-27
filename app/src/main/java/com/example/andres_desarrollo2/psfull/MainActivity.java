package com.example.andres_desarrollo2.psfull;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andres_desarrollo2.psfull.Beans.RadicadoBn;
import com.example.andres_desarrollo2.psfull.Beans.TipologiaBn;
import com.example.andres_desarrollo2.psfull.Control.Constantes;
import com.example.andres_desarrollo2.psfull.Control.CustonAdapter;
import com.example.andres_desarrollo2.psfull.Control.Session;
import com.example.andres_desarrollo2.psfull.Server.ServerRequest;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CustonAdapter<RadicadoBn> adapter;
    List<RadicadoBn> list;
    ListView lista1;
    SearchManager searchManager;
    RadicadoBn radicado;
    Bundle bundle;
    List<NameValuePair> params;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, CrearRadicadosActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        finByAllRadicados();
        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(getApplicationContext(), AsignarRadicadoActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(getApplicationContext(), EjecutarActividadActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void finByAllRadicados() {
        Log.d("Entra a la lista", "Si");
        list = new ArrayList<RadicadoBn>();
        lista1 = (ListView) findViewById(R.id.miLista);
        params = new ArrayList<NameValuePair>();
        ServerRequest sr = new ServerRequest();

        JSONObject json = sr.getJSON(Constantes.URL_LIST_RADICADO, params, ServerRequest.GET);

        if (json != null) {
            Log.d("Encuentra datos", "Si");
            try {
                if (json.getBoolean("res")) {
                    JSONArray categories = json.getJSONArray(Constantes.JSONArray_RADICADO);

                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);

                        TipologiaBn  tipologias = null;
                        JSONArray tipologiasJS = catObj.getJSONArray("tipologias");
                        for (int t = 0; t < tipologiasJS.length(); t++) {
                            JSONObject objTip = (JSONObject) tipologiasJS.get(t);
                            tipologias = new TipologiaBn(objTip.getString("codigo"), objTip.getString("descripcion"));
                        }

                        radicado = new RadicadoBn(catObj.getString("titulo"), catObj.getString("descripcion"), tipologias);

                        list.add(radicado);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No existen registros en la base de datos", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new CustonAdapter<RadicadoBn>(this, list);

        lista1.setAdapter(adapter);
    }
}
