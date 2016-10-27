package com.example.andres_desarrollo2.psfull;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andres_desarrollo2.psfull.Control.Constantes;
import com.example.andres_desarrollo2.psfull.Control.Utils;
import com.example.andres_desarrollo2.psfull.Server.ServerRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrarCuentaActivity extends AppCompatActivity implements View.OnClickListener {

    String email = "", login = "", pass = "";
    EditText edt_login, edt_pass, edt_email;
    Button btn_Regis, btnCal;
    List<NameValuePair> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cuenta);
        edt_login = (EditText) findViewById(R.id.edtUser);
        edt_pass = (EditText) findViewById(R.id.edtPassw);
        edt_email = (EditText) findViewById(R.id.edtEmail);

        btn_Regis = (Button) findViewById(R.id.btnRegis);
        btn_Regis.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegis:
                savedPreferences();
                break;
        }
    }

    private void savedPreferences() {
        login = edt_login.getText().toString();
        pass = edt_pass.getText().toString();
        email = edt_email.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(login) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Ha dejado campos vacios", Toast.LENGTH_LONG).show();
        } else {
            if (Utils.validaEmail(email)) {

                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user", login));
                params.add(new BasicNameValuePair("password", pass));
                params.add(new BasicNameValuePair("email", email));
                ServerRequest sr = new ServerRequest();
                JSONObject json = sr.getJSON(Constantes.URL_REGISTER, params, ServerRequest.POST);

                if (json != null) {
                    try {
                        String jsonstr = json.getString("response");
                        String token = json.getString("token");
                        String pass = json.getString("pass");

                        SharedPreferences sharedPref = getSharedPreferences(Constantes.REFERENCIA_USER, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(Constantes.KEY_TOKEN, token);
                        editor.putString(Constantes.KEY_PASSW, pass);

                        Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();

                        editor.commit();
                        finish();

                        Log.d("Mensaje de registro", jsonstr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Por favor valide el email", Toast.LENGTH_LONG).show();
            }
        }
    }
}
