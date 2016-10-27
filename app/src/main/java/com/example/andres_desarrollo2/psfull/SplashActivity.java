package com.example.andres_desarrollo2.psfull;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.andres_desarrollo2.psfull.Control.Session;

public class SplashActivity extends AppCompatActivity {

    public static final int segundos = 8;
    public static final int milisegundos = segundos * 1000;
    public static final int delay = 2;
    private ProgressBar pbprogreso;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        pbprogreso = (ProgressBar) findViewById(R.id.bprogreso);
        pbprogreso.setMax(maximoprogreso());

        session = new Session(this);
        if(session.loggedin()){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }

        empezaranimacion();
    }

    public void empezaranimacion() {
        new CountDownTimer(milisegundos, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                pbprogreso.setProgress(establecerprogreso(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }

    public int establecerprogreso(long miliseconds) {
        return (int) ((milisegundos - miliseconds) / 1000);
    }

    public int maximoprogreso() {
        return segundos - delay;
    }
}
