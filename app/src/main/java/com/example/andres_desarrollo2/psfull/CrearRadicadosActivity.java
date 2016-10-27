package com.example.andres_desarrollo2.psfull;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andres_desarrollo2.psfull.Beans.CentropeBn;
import com.example.andres_desarrollo2.psfull.Beans.RadicadoBn;
import com.example.andres_desarrollo2.psfull.Beans.TipologiaBn;
import com.example.andres_desarrollo2.psfull.Control.Constantes;
import com.example.andres_desarrollo2.psfull.Control.CustonAdapter;
import com.example.andres_desarrollo2.psfull.Control.Dialogs;
import com.example.andres_desarrollo2.psfull.Control.Utils;
import com.example.andres_desarrollo2.psfull.Server.ServerRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrearRadicadosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner comboLugar, comboTipologia;
    EditText edtTitulo, edtDescripcion;
    private String titulo = null,selectedLugar = null, selectedTipologia = null, descripcion = null;
    String codLugar, codTipo;
    private CentropeBn centropes;
    private TipologiaBn tipologias;
    List<NameValuePair> params;
    private int consecutivo;

    private ArrayList<CentropeBn> centropeList;
    private ArrayList<TipologiaBn> tipologiaList;
    private RadicadoBn radicado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_radicados);

        comboLugar = (Spinner) findViewById(R.id.combo_lugar);
        comboTipologia = (Spinner) findViewById(R.id.combo_tipoLogia);

        edtTitulo = (EditText)findViewById(R.id.edt_Titulo);
        edtDescripcion= (EditText)findViewById(R.id.edt_Descripcion);

        centropeList = new ArrayList<CentropeBn>();
        tipologiaList = new ArrayList<TipologiaBn>();

        // spinner item select listener
        comboLugar.setOnItemSelectedListener(this);
        comboTipologia.setOnItemSelectedListener(this);

        centrope();
        tipologia();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        //check which spinner triggered the listener
        switch (parent.getId()) {
            //country spinner
            case R.id.combo_lugar:
                //make sure the country was already selected during the onCreate
                if (selectedLugar != null) {
                    Toast.makeText(parent.getContext(), "Lugar selecionado: " + selectedItem, Toast.LENGTH_LONG).show();
                }

                selectedLugar = selectedItem;
                findByrDescripCentrope();
                centropes = new CentropeBn(codLugar,selectedLugar);
                break;
            //animal spinner
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

    public void findByrDescripCentrope() {
        List<NameValuePair> valueLugar = new ArrayList<NameValuePair>();
        valueLugar.add(new BasicNameValuePair("descripcion", selectedLugar));
        ServerRequest sr = new ServerRequest();
        JSONObject jsonLugar = sr.getJSON(Constantes.URL_CENTROPE_DES, valueLugar, ServerRequest.POST);

        if (jsonLugar != null) {
            try {
                if (jsonLugar.getBoolean("res")) {
                    codLugar = jsonLugar.getString("codigo");
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
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
                        Toast.makeText(CrearRadicadosActivity.this, "No existen registros en la base de datos", Toast.LENGTH_SHORT).show();
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

    private void centrope() {
        try {
            params = new ArrayList<NameValuePair>();
            ServerRequest sr = new ServerRequest();

            JSONObject json = sr.getJSON(Constantes.URL_CENTROPE, params, ServerRequest.GET);

            if (json != null) {
                try {
                    if (json.getBoolean("res")) {
                        JSONArray categories = json.getJSONArray(Constantes.JSONArray_CENTROPE);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            CentropeBn cat = new CentropeBn(catObj.getString("codigo"), catObj.getString("descripcion"));

                            centropeList.add(cat);
                        }
                    } else {
                        Toast.makeText(CrearRadicadosActivity.this, "No existen registros en la base de datos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Creating adapter for spinner
            List<String> listaCent = new ArrayList<String>();
            for (int i = 0; i < centropeList.size(); i++) {
                listaCent.add(centropeList.get(i).getDescripcion());
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCent);
            // Drop down layout style - list view with radio button
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            comboLugar.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelar(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void guardar(View view) {
        titulo = edtTitulo.getText().toString();
        descripcion = edtDescripcion.getText().toString();

        if(TextUtils.isEmpty(titulo)||TextUtils.isEmpty(descripcion)){
            Toast.makeText(this, "Ha dejado campos vacios", Toast.LENGTH_LONG).show();
        }else{
            String cod = codigoConsecutivo("");
            radicado =  new RadicadoBn(cod,titulo,descripcion, centropes, tipologias);

            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("codigo", radicado.getCodigo()));
            params.add(new BasicNameValuePair("titulo", radicado.getTitulo()));
            params.add(new BasicNameValuePair("consecutivo", consecutivo+""));

            params.add(new BasicNameValuePair("centroCod", radicado.getCentropes().getCodigo()));
            params.add(new BasicNameValuePair("centroDes", radicado.getCentropes().getDescripcion()));

            params.add(new BasicNameValuePair("tipoCod", radicado.getTipologias().getCodigo()));
            params.add(new BasicNameValuePair("tipoDes", radicado.getTipologias().getDescripcion()));

            params.add(new BasicNameValuePair("descripcion", radicado.getDescripcion()));
            ServerRequest sr = new ServerRequest();
            JSONObject json = sr.getJSON(Constantes.URL_REGISTER_RADICADO, params, ServerRequest.POST);

            if (json != null) {
                try {
                    String jsonstr = json.getString("response");
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String codigoConsecutivo(String valor){
        String codigo ="";
        boolean existe = true;

        while(existe) {
            List<NameValuePair> paramsConse = new ArrayList<NameValuePair>();
            paramsConse.add(new BasicNameValuePair("codigo", valor));
            ServerRequest sr = new ServerRequest();

            JSONObject json = sr.getJSON(Constantes.URL_CONSECUTIVO_RADICADO, paramsConse, ServerRequest.GET);

            if (json != null) {
                try {
                    existe = json.getBoolean("res");
                    codigo = json.getString("codigo");

                    if(!existe){
                        consecutivo = json.getInt("consec")+1;
                        if(consecutivo <= 9){
                            codigo = "rad0"+consecutivo;
                        }else{
                            codigo = "rad"+consecutivo;
                        }
                        valor = codigo;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return codigo;
    }
}
