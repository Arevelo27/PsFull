package com.example.andres_desarrollo2.psfull;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andres_desarrollo2.psfull.Control.Constantes;
import com.example.andres_desarrollo2.psfull.Control.Dialogs;
import com.example.andres_desarrollo2.psfull.Control.Session;
import com.example.andres_desarrollo2.psfull.Control.Utils;
import com.example.andres_desarrollo2.psfull.Server.ServerRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edtUsers, edtPassword;
    TextView edtRegis, edtRecuperar;
    CheckBox checkPass;
    Button btnLog, btnExi;
    String user, password;
    Intent intent;
    DialogFragment newFragment;
    Bundle bundle;
    List<NameValuePair> params;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(this);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        // Set up the login form.

        edtUsers = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassw);

        edtRecuperar = (TextView) findViewById(R.id.txtVwLoginRecuperarCuenta);
        edtRecuperar.setOnClickListener(this);

        edtRegis = (TextView) findViewById(R.id.txtVwRegistro);
        edtRegis.setOnClickListener(this);

        btnLog = (Button) findViewById(R.id.btnLogin);
        btnLog.setOnClickListener(this);

        btnExi = (Button) findViewById(R.id.btnExit);
        btnExi.setOnClickListener(this);

        checkPass = (CheckBox) findViewById(R.id.chkShow);
        checkPass.setOnClickListener(this);

        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                validarLogin();
                break;
            case R.id.chkShow:
                mostrarPassword();
                break;
            case R.id.btnExit:
                finish();
                break;
            case R.id.txtVwRegistro:
                crearCuenta();
                break;
            case R.id.txtVwLoginRecuperarCuenta:
                recuperarCuenta();
                break;
        }
    }

    private void mostrarPassword() {
        checkPass = (CheckBox) findViewById(R.id.chkShow);
        if (checkPass.isChecked()) {
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void validarLogin() {

        user = edtUsers.getText().toString();
        password = edtPassword.getText().toString();

        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", user));
        params.add(new BasicNameValuePair("password", password));
        ServerRequest sr = new ServerRequest();
        JSONObject json = sr.getJSON(Constantes.URL_LOGIN, params, ServerRequest.POST);
        String key_user, key_passw;

        if (json != null) {
            try {
                String jsonstr = json.getString("response");
                if (json.getBoolean("res")) {

                    SharedPreferences sp = getSharedPreferences(Constantes.REFERENCIA_USER, MODE_PRIVATE);
                    key_user = sp.getString(Constantes.KEY_TOKEN, "");
                    key_passw = sp.getString(Constantes.KEY_PASSW, "");

                    //if (user.equals(key_user) && password.equals(key_passw)) {
                    String btnPositivo, btnNegatrivo;
                    btnPositivo = getResources().getString(R.string.btn_Ok);
                    btnNegatrivo = getResources().getString(R.string.btn_CANCEL);

                    session.setLoggedin(true);

                    DialogFragment newFragment = new Dialogs();
                    bundle = Utils.retonarBundleDialog("Welcome!", "Welcome user " + user, "Login", 1, btnPositivo, btnNegatrivo);
                    //Envía paquete
                    newFragment.setArguments(bundle);
                    newFragment.show(getFragmentManager(), "Welcome");


                } else {
                    Toast.makeText(LoginActivity.this, "Password incorrecto", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void crearCuenta() {
        startActivity(new Intent(LoginActivity.this, RegistrarCuentaActivity.class));
    }

    private void recuperarCuenta() {
        startActivity(new Intent(LoginActivity.this, RecuperarCuentaActivity.class));
    }
}


