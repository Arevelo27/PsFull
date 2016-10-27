package com.example.andres_desarrollo2.psfull;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andres_desarrollo2.psfull.Beans.RadicadoBn;
import com.example.andres_desarrollo2.psfull.Beans.TipologiaBn;
import com.example.andres_desarrollo2.psfull.Control.Constantes;
import com.example.andres_desarrollo2.psfull.Control.CustonAdapter;
import com.example.andres_desarrollo2.psfull.Server.ServerRequest;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class contentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

    }
}
