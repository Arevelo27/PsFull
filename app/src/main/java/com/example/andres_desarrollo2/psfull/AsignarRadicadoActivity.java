package com.example.andres_desarrollo2.psfull;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andres_desarrollo2.psfull.Beans.CentropeBn;
import com.example.andres_desarrollo2.psfull.Beans.EstadoBn;
import com.example.andres_desarrollo2.psfull.Beans.TipologiaBn;
import com.example.andres_desarrollo2.psfull.Control.Constantes;
import com.example.andres_desarrollo2.psfull.Server.ServerRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AsignarRadicadoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner comboEstado, comboTecnio, comboTipologia, comboMaterial;
    String codEstado, codTecnico, codTipo, codMaterial;
    private String titulo = null,selectedEstado = null, selectedTipologia = null, descripcion = null;

    private TipologiaBn tipologias;
    private EstadoBn estados;
    List<NameValuePair> params;
    private int consecutivo;

    private ArrayList<EstadoBn> estadoList;
    private ArrayList<TipologiaBn> tipologiaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_radicado);

        comboEstado = (Spinner) findViewById(R.id.combo_estado);
        comboTipologia = (Spinner) findViewById(R.id.combo_tipoLogia);

        estadoList = new ArrayList<EstadoBn>();
        tipologiaList = new ArrayList<TipologiaBn>();

        comboEstado.setOnItemSelectedListener(this);
        comboTipologia.setOnItemSelectedListener(this);
        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        estado();
        tipologia();
    }


    private void estado() {
        try {
            params = new ArrayList<NameValuePair>();
            ServerRequest sr = new ServerRequest();

            JSONObject json = sr.getJSON(Constantes.URL_ESTADO, params, ServerRequest.GET);

            if (json != null) {
                try {
                    if (json.getBoolean("res")) {
                        JSONArray categories = json.getJSONArray(Constantes.JSONArray_ESTADO);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            EstadoBn cat = new EstadoBn(catObj.getString("codigo"), catObj.getString("descripcion"));

                            estadoList.add(cat);
                        }
                    } else {
                        Toast.makeText(AsignarRadicadoActivity.this, "No existen registros en la base de datos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Creating adapter for spinner
            List<String> listaEstado = new ArrayList<String>();
            for (int i = 0; i < estadoList.size(); i++) {
                listaEstado.add(estadoList.get(i).getDescripcion());
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaEstado);
            // Drop down layout style - list view with radio button
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            comboEstado.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void tipologia() {
        try {
            params = new ArrayList<NameValuePair>();
            ServerRequest sr = new ServerRequest();

            JSONObject json = sr.getJSON(Constantes.URL_TIPOLOGIA, params, ServerRequest.GET);

            if (json != null) {
                try {
                    if (json.getBoolean("res")) {
                        JSONArray categories = json.getJSONArray(Constantes.JSONArray_TIPOLOGIA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            TipologiaBn cat = new TipologiaBn(catObj.getString("codigo"), catObj.getString("descripcion"));

                            tipologiaList.add(cat);
                        }
                    } else {
                        Toast.makeText(AsignarRadicadoActivity.this, "No existen registros en la base de datos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Creating adapter for spinner
            List<String> listaTipo = new ArrayList<String>();
            for (int i = 0; i < tipologiaList.size(); i++) {
                listaTipo.add(tipologiaList.get(i).getDescripcion());
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTipo);
            // Drop down layout style - list view with radio button
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            comboTipologia.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            startActivity(new Intent(AsignarRadicadoActivity.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        //check which spinner triggered the listener
        switch (parent.getId()) {

            //ESTADOS
            case R.id.combo_estado:
                //make sure the animal was already selected during the onCreate

                if (selectedEstado != null) {
                    Toast.makeText(parent.getContext(), "Estado selecionado: " + selectedItem, Toast.LENGTH_LONG).show();
                }
                selectedEstado = selectedItem;
                findByrDescripEstado();
                estados = new EstadoBn(codEstado,selectedEstado);
                break;

            //TIPOLOGIAS
            case R.id.combo_tipoLogia:
                //make sure the animal was already selected during the onCreate

                if (selectedTipologia != null) {
                    Toast.makeText(parent.getContext(), "Tipologia selecionada: " + selectedItem, Toast.LENGTH_LONG).show();
                }
                selectedTipologia = selectedItem;
                findByrDescripTipologia();
                tipologias = new TipologiaBn(codTipo,selectedTipologia);
                break;
        }
    }

    public void findByrDescripEstado() {
        List<NameValuePair> valueEstado = new ArrayList<NameValuePair>();
        valueEstado.add(new BasicNameValuePair("descripcion", selectedEstado));
        ServerRequest sr = new ServerRequest();
        JSONObject jsonEstado = sr.getJSON(Constantes.URL_ESTADO_DES, valueEstado, ServerRequest.POST);

        if (jsonEstado != null) {
            try {
                if (jsonEstado.getBoolean("res")) {
                    codEstado = jsonEstado.getString("codigo");
                }
            }catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void findByrDescripTipologia() {
        List<NameValuePair> valueTipologia = new ArrayList<NameValuePair>();
        valueTipologia.add(new BasicNameValuePair("descripcion", selectedTipologia));
        ServerRequest sr = new ServerRequest();
        JSONObject jsonTipologia = sr.getJSON(Constantes.URL_TIPOLOGIA_DES, valueTipologia, ServerRequest.POST);

        if (jsonTipologia != null) {
            try {
                if (jsonTipologia.getBoolean("res")) {
                    codTipo = jsonTipologia.getString("codigo");
                }
            }catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
